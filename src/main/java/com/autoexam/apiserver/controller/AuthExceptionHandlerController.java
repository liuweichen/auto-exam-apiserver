package com.autoexam.apiserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthExceptionHandlerController {

  @RequestMapping("authExceptionHandler")
  public ResponseEntity authExceptionHandler() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
