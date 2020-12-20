package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.*;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController extends ExceptionHandlerController {
  @Autowired
  private TeacherService teacherService;
  @Autowired
  private CourseService courseService;
  @Autowired
  private ChapterService chapterService;
  @Autowired
  private QuestionService questionService;
  @Autowired
  private ExamService examService;

  @GetMapping("/students/{student_id}/teachers")
  public List<Teacher> getTeachers() {
    return teacherService.getAll();
  }

  @GetMapping("/students/{student_id}/courses")
  public List<Course> getCourses(
    @RequestParam(value = "teacher_id", required = false) Long teacherId,
    @RequestParam(value = "course_id", required = false) Long courseId
  ) {
    return courseService.getAll(teacherId, courseId);
  }

  @GetMapping("/students/{student_id}/chapters")
  public List<Chapter> getChapters(
    @RequestParam(value = "teacher_id", required = false) Long teacherId,
    @RequestParam(value = "course_id", required = false) Long courseId,
    @RequestParam(value = "chapter_id", required = false) Long chapterId
  ) {
    return chapterService.getAll(teacherId, courseId, chapterId);
  }

  @GetMapping("/students/{student_id}/exams")
  public List<Exam> getExams(
    @RequestParam(value = "teacher_id", required = false) Long teacherId,
    @RequestParam(value = "course_id", required = false) Long courseId,
    @RequestParam(value = "exam_id", required = false) Long examId
  ) {
    return examService.getAll(teacherId, courseId, examId);
  }

  @GetMapping("/students/{student_id}/exams/{exam_id}/questions")
  public List<Question> getExamQuestions(
    @PathVariable(value = "exam_id", required = false) Long examId
  ) {
    return examService.getQuestionsByExamId(examId);
  }

  @GetMapping("/students/{student_id}/questions")
  public Page<Question> getQuestions(
    @RequestParam(value = "teacher_id", required = false) Long teacherId,
    @RequestParam(value = "course_id", required = false) Long courseId,
    @RequestParam(value = "chapter_id", required = false) Long chapterId,
    @RequestParam(value = "question_id", required = false) Long questionId,
    @RequestParam(value = "type", required = false) Integer type,
    @RequestParam(value = "current_page", required = false, defaultValue = "1") Integer currentPage,
    @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize,
    @RequestParam(value = "sort_field", required = false, defaultValue = "updatedAt") String sortField,
    @RequestParam(value = "sort_order", required = false, defaultValue = "desc") String sortOrder
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable page = PageRequest.of(currentPage - 1, pageSize, sort);
    return questionService.getQuestionPage(teacherId, courseId, chapterId, questionId, type, page);
  }
}
