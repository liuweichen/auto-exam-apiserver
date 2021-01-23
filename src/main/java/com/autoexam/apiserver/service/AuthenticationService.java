package com.autoexam.apiserver.service;

import cn.hutool.core.date.DateUtil;
import com.autoexam.apiserver.beans.Admin;
import com.autoexam.apiserver.beans.Student;
import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.dao.AdminDao;
import com.autoexam.apiserver.dao.StudentDao;
import com.autoexam.apiserver.dao.TeacherDao;
import com.autoexam.apiserver.exception.AuthenticationException;
import com.autoexam.apiserver.model.AuthenticationInfo;
import com.autoexam.apiserver.model.response.Token;
import com.autoexam.apiserver.service.common.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationService {
  @Autowired
  private AdminDao adminDao;
  @Autowired
  private TeacherDao teacherDao;
  @Autowired
  private StudentDao studentDao;
  @Autowired
  private JwtTokenService jwtTokenService;
  @Autowired
  private BCryptPasswordEncoder encoder;
  @Value("${token.valid.hours:1}")
  private Integer tokenValidHour;
  @Value("${token.fresh.tolerate.second:10}")
  private Integer tolerateSecond;

  public Token adminLogin(Admin admin) {
    Admin t = adminDao.getOneByName(admin.getName()).orElseThrow(() -> new AuthenticationException("用户不存在"));
    if (!encoder.matches(admin.getPassword(), t.getPassword())) {
      log.info("password error for admin: {}", admin.getName());
      throw new AuthenticationException("密码错误");
    } else {
      return getToken(t.getName(), t.getId(), "admin", t.getHostName());
    }
  }

  public Token teacherLogin(Teacher teacher) {
    Teacher t = teacherDao.getOneByName(teacher.getName()).orElseThrow(() -> new AuthenticationException("用户不存在"));
    if (!encoder.matches(teacher.getPassword(), t.getPassword())) {
      log.info("password error for teacher: {}", teacher.getName());
      throw new AuthenticationException("密码错误");
    } else {
      return getToken(t.getName(), t.getId(), "teacher", adminDao.getOne(t.getAdminId()).getHostName());
    }
  }

  public Token studentLogin(Student student) {
    Student t = studentDao.getOneByName(student.getName()).orElseThrow(() -> new AuthenticationException("用户不存在"));
    if (!encoder.matches(student.getPassword(), t.getPassword())) {
      log.info("password error for student: {}", student.getName());
      throw new AuthenticationException("密码错误");
    } else {
      return getToken(t.getName(), t.getId(), "student", null);
    }
  }

  public Token refreshToken(String token) {
    AuthenticationInfo auth = jwtTokenService.verifyToken(token, tolerateSecond);
    return getToken(auth.getUserName(), auth.getUserId(), auth.getRole(), auth.getHostName());
  }

  private Token getToken(String name, long id, String role, String hostName) {
    AuthenticationInfo auth = new AuthenticationInfo();
    Date now = new Date();
    Date exp = DateUtil.offsetHour(now, tokenValidHour);
    auth.setIat(now.getTime());
    auth.setExp(exp.getTime());
    auth.setJti(UUID.randomUUID().toString());
    auth.setRole(role);
    auth.setUserName(name);
    auth.setUserId(id);
    auth.setHostName(hostName);
    return new Token(jwtTokenService.generateToken(auth), exp.getTime());
  }
}
