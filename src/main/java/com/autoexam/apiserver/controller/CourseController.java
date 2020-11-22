package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Course;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.IDJson;
import com.autoexam.apiserver.service.CourseService;
import com.autoexam.apiserver.service.privilege.TeacherPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CourseController extends ExceptionHandlerController {
  @Autowired
  private CourseService service;
  @Autowired
  private TeacherPrivilegeService privilegeService;

  @TraceLog(clazz = "CourseController", method = "createCourse")
  @PostMapping("/teachers/{teacher_id}/courses")
  public IDJson createCourse(
    @PathVariable("teacher_id") Long teacherId,
    @Valid @RequestBody Course course) {
    course.setTeacherId(teacherId);
    return service.save(course);
  }

  @TraceLog(clazz = "CourseController", method = "updateCourse")
  @PutMapping("/teachers/{teacher_id}/courses/{course_id}")
  public void updateCourse(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("course_id") Long courseId,
    @Valid @RequestBody Course course) {
    privilegeService.checkTeacherHasCourse(teacherId, courseId);
    course.setTeacherId(teacherId);
    course.setId(courseId);
    service.update(course);
  }

  @GetMapping("/teachers/{teacher_id}/courses")
  public List<Course> getCourses(@PathVariable("teacher_id") Long teacherId) {
    return service.getAll(teacherId, null);
  }

  @TraceLog(clazz = "CourseController", method = "deleteCourse")
  @DeleteMapping("/teachers/{teacher_id}/courses/{course_id}")
  public void deleteCourse(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("course_id") Long courseId) {
    privilegeService.checkTeacherHasCourse(teacherId, courseId);
    service.deleteById(courseId);
  }
}
