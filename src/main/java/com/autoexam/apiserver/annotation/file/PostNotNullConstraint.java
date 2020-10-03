package com.autoexam.apiserver.annotation.file;

import io.swagger.models.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PostNotNullConstraint implements ConstraintValidator<PostNotNull, Object> {
  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    if (HttpMethod.POST.name().equalsIgnoreCase(request.getMethod()) && null == object) {
      return false;
    } else {
      return true;
    }
  }
}
