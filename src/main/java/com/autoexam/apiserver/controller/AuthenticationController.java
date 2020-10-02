package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.Admin;
import com.autoexam.apiserver.beans.Student;
import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.model.response.Token;
import com.autoexam.apiserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
  @Autowired
  private AuthenticationService service;

  @PostMapping("/login/admin")
  public Token adminLogin(
    @Valid @RequestBody Admin admin) {
    return service.adminLogin(admin);
  }

  @PostMapping("/login/teacher")
  public Token teacherLogin(
    @Valid @RequestBody Teacher teacher) {
    return service.teacherLogin(teacher);
  }

  @PostMapping("/login/student")
  public Token studentLogin(
    @Valid @RequestBody Student student) {
    return service.studentLogin(student);
  }
}
