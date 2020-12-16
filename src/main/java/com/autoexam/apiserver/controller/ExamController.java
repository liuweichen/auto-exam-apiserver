package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Exam;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.IDJson;
import com.autoexam.apiserver.service.ExamService;
import com.autoexam.apiserver.service.privilege.TeacherPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ExamController extends ExceptionHandlerController {
  @Autowired
  private ExamService service;
  @Autowired
  private TeacherPrivilegeService privilegeService;

  @TraceLog(clazz = "ExamController", method = "createExam")
  @PostMapping("/teachers/{teacher_id}/exams")
  public IDJson createExam(
    @PathVariable("teacher_id") Long teacherId,
    @Valid @RequestBody Exam exam) {
    privilegeService.checkTeacherHasCourse(teacherId, exam.getCourseId());
    return service.save(exam);
  }

  @TraceLog(clazz = "ExamController", method = "updateExam")
  @PutMapping("/teachers/{teacher_id}/exams/{exam_id}")
  public void updateExam(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("exam_id") Long examId,
    @Valid @RequestBody Exam exam) {
    privilegeService.checkTeacherHasExam(teacherId, examId);
    if (!StringUtils.isEmpty(exam.getCourseId())) {
      privilegeService.checkTeacherHasCourse(teacherId, exam.getCourseId());
    }
    exam.setId(examId);
    service.update(exam);
  }

  @GetMapping("/teachers/{teacher_id}/exams")
  public List<Exam> getChapters(
    @PathVariable("teacher_id") Long teacherId,
    @RequestParam(value = "course_id", required = false) Long courseId,
    @RequestParam(value = "exam_id", required = false) Long examId) {
    return service.getAll(teacherId, courseId, examId);
  }

  @TraceLog(clazz = "ExamController", method = "deleteExam")
  @DeleteMapping("/teachers/{teacher_id}/exams/{exam_id}")
  public void deleteExam(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("exam_id") Long examId) {
    privilegeService.checkTeacherHasExam(teacherId, examId);
    privilegeService.checkExamNotHasQuestions(examId);
    service.deleteById(examId);
  }
}
