package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Admin;
import com.autoexam.apiserver.beans.Student;
import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.Token;
import com.autoexam.apiserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController extends ExceptionHandlerController {
  @Autowired
  private AuthenticationService service;

  @TraceLog(clazz = "AuthenticationController", method = "adminLogin")
  @PostMapping("/login/admin")
  public Token adminLogin(
    @Valid @RequestBody Admin admin) {
    return service.adminLogin(admin);
  }

  @TraceLog(clazz = "AuthenticationController", method = "teacherLogin")
  @PostMapping("/login/teacher")
  public Token teacherLogin(
    @Valid @RequestBody Teacher teacher) {
    return service.teacherLogin(teacher);
  }

  @TraceLog(clazz = "AuthenticationController", method = "studentLogin")
  @PostMapping("/login/student")
  public Token studentLogin(
    @Valid @RequestBody Student student) {
    return service.studentLogin(student);
  }
}
