package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.TeacherOverview;
import com.autoexam.apiserver.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OverviewController extends ExceptionHandlerController {
  @Autowired
  private OverviewService service;

  @GetMapping("/teachers/{teacher_id}/overview")
  public TeacherOverview getCourses(@PathVariable("teacher_id") Long teacherId) {
    return service.getOverview(teacherId);
  }
}
