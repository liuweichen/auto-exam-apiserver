package com.autoexam.apiserver.controller.base;

import com.autoexam.apiserver.model.ErrorJson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthExceptionHandlerController {
  @RequestMapping("/authExceptionHandler")
  public ResponseEntity authExceptionHandler(@RequestParam(value = "error_msg", required = false) String errorMsg) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorJson(errorMsg));
  }
}
