package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.*;
import com.autoexam.apiserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {
  @Autowired
  private TeacherService teacherService;
  @Autowired
  private CourseService courseService;
  @Autowired
  private ChapterService chapterService;
  @Autowired
  private QuestionService questionService;
  @Autowired
  private AnswerService answerService;

  @GetMapping("/students/{student_id}/teachers")
  public List<Teacher> getTeachers() {
    return teacherService.getAll();
  }

  @GetMapping("/students/{student_id}/teachers/{teacher_id}/courses")
  public List<Course> getCourses(@PathVariable("teacher_id") Long teacherId) {
    return courseService.getAll(teacherId);
  }

  @GetMapping("/students/{student_id}/courses/{course_id}/chapters")
  public List<Chapter> getChapters(
    @PathVariable("course_id") Long courseId) {
    return chapterService.getAll(courseId);
  }

  @GetMapping("/students/{student_id}/chapters/{chapter_id}/questions")
  public Page<Question> getQuestions(
    @PathVariable("chapter_id") Long chapterId,
    @RequestParam(value = "current_page", required = false, defaultValue = "1") Integer currentPage,
    @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize
  ) {
    Pageable page = PageRequest.of(currentPage - 1, pageSize);
    return questionService.getQuestionPage(chapterId, page);
  }

  @GetMapping("/students/{student_id}/questions/{question_id}/answers")
  public List<Answer> getAnswers(
    @PathVariable("course_id") Long questionId) {
    return answerService.getAll(questionId);
  }
}
