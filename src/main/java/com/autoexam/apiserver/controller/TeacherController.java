package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TeacherController extends ExceptionHandlerController {
  @Autowired
  private TeacherService service;

  @PostMapping("/admins/{admin_id}/teachers")
  public void createTeacher(
    @PathVariable("admin_id") Long adminId,
    @Valid @RequestBody Teacher teacher) {
    teacher.setAdminId(adminId);
    service.save(teacher);
  }

  @PutMapping("/admins/{admin_id}/teachers/{teacher_id}")
  public void updateTeacher(
    @PathVariable("admin_id") Long adminId,
    @PathVariable("teacher_id") Long teacherId,
    @Valid @RequestBody Teacher teacher) {
    teacher.setAdminId(adminId);
    teacher.setId(teacherId);
    service.update(teacher);
  }

  @GetMapping("/admins/{admin_id}/teachers")
  public List<Teacher> getTeachers(@PathVariable("admin_id") Long adminId) {
    return service.getAll(adminId);
  }

  @DeleteMapping("/admins/{admin_id}/teachers/{teacher_id}")
  public void deleteTeacher(
    @PathVariable("admin_id") Long adminId,
    @PathVariable("teacher_id") Long teacherId) {
    service.deleteById(teacherId);
  }
}
