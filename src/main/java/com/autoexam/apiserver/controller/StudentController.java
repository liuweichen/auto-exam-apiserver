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

  @GetMapping("/students/{student_id}/teachers")
  public List<Teacher> getTeachers() {
    return teacherService.getAll();
  }

  @GetMapping("/students/{student_id}/courses")
  public List<Course> getCourses(@RequestParam("teacher_id") Long teacherId) {
    return courseService.getAll(teacherId);
  }

  @GetMapping("/students/{student_id}/chapters")
  public List<Chapter> getChapters(
    @RequestParam("course_id") Long courseId) {
    return chapterService.getAll(null, courseId, null);
  }

  @GetMapping("/students/{student_id}/questions")
  public Page<Question> getQuestions(
    @RequestParam("chapter_id") Long chapterId,
    @RequestParam(value = "current_page", required = false, defaultValue = "1") Integer currentPage,
    @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize,
    @RequestParam(value = "sort_field", required = false, defaultValue = "updated_at") String sortField,
    @RequestParam(value = "sort_order", required = false, defaultValue = "desc") String sortOrder
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable page = PageRequest.of(currentPage - 1, pageSize, sort);
    return questionService.getQuestionPage(null, null, chapterId, null, null, page);
  }
}
