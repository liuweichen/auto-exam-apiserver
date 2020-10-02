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

  public Token adminLogin(Admin admin) {
    Admin t = adminDao.getOneByName(admin.getName()).orElseThrow(() -> new AuthenticationException("用户不存在"));
    if (!t.getPassword().equals(admin.getPassword())) {
      log.info("password error for admin: {}", admin.getName());
      throw new AuthenticationException("密码错误");
    } else {
      return new Token(jwtTokenService.generateToken(getAuth(t.getName(), t.getId())));
    }
  }

  public Token teacherLogin(Teacher teacher) {
    Teacher t = teacherDao.getOneByName(teacher.getName()).orElseThrow(() -> new AuthenticationException("用户不存在"));
    if (!t.getPassword().equals(teacher.getPassword())) {
      log.info("password error for teacher: {}", teacher.getName());
      throw new AuthenticationException("密码错误");
    } else {
      return new Token(jwtTokenService.generateToken(getAuth(t.getName(), t.getId())));
    }
  }

  public Token studentLogin(Student student) {
    Student t = studentDao.getOneByName(student.getName()).orElseThrow(() -> new AuthenticationException("用户不存在"));
    if (!t.getPassword().equals(student.getPassword())) {
      log.info("password error for student: {}", student.getName());
      throw new AuthenticationException("密码错误");
    } else {
      return new Token(jwtTokenService.generateToken(getAuth(t.getName(), t.getId())));
    }
  }

  private AuthenticationInfo getAuth(String name, long id) {
    AuthenticationInfo auth = new AuthenticationInfo();
    Date now = new Date();
    Date exp = DateUtil.offsetSecond(now, 60 * 60);
    auth.setIat(now.getTime());
    auth.setExp(exp.getTime());
    auth.setJti(UUID.randomUUID().toString());
    auth.setUserName(name);
    auth.setUserId(id);
    return auth;
  }
}
