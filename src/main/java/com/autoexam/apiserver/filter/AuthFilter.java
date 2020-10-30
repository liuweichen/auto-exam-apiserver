package com.autoexam.apiserver.filter;

import com.autoexam.apiserver.exception.AuthenticationException;
import com.autoexam.apiserver.model.AuthenticationInfo;
import com.autoexam.apiserver.service.common.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class AuthFilter implements Filter {
  @Value("${autoexam.auth.enabled:true}")
  private Boolean authEnabled;
  @Autowired
  private JwtTokenService jwtTokenService;
  private Set<String> whiteList = new HashSet<>(Arrays.asList(
    "/login/admin",
    "/login/teacher",
    "/login/student",
    "/token/refresh"
  ));

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (authEnabled) {
      if (request.getScheme().equalsIgnoreCase("https")) {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        if (whiteList.contains(uri)) {
          chain.doFilter(request, response);
        } else {
          long userId = Long.valueOf(uri.split("/")[2]);
          String token = req.getHeader("token");
          if (StringUtils.isEmpty(token)) {
            request.getRequestDispatcher("/authExceptionHandler?error_msg=token为空").forward(request, response);
          }
          try {
            AuthenticationInfo auth = jwtTokenService.verifyToken(token, 0);
            if (userId == auth.getUserId()) {
              chain.doFilter(request, response);
            } else {
              request.getRequestDispatcher("/authExceptionHandler?error_msg=用户ID和token不一致").forward(request, response);
            }
          } catch (AuthenticationException e) {
            request.getRequestDispatcher("/authExceptionHandler?error_msg=" + e.getMessage()).forward(request, response);
          }
        }
      } else {
        chain.doFilter(request, response);
      }
    } else {
      chain.doFilter(request, response);
    }
  }
}
