package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.beans.Question;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class QuestionController extends ExceptionHandlerController {
  @Autowired
  private QuestionService service;

  @PostMapping("/teachers/{teacher_id}/chapters/{chapter_id}/questions")
  public void createQuestion(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("chapter_id") Long chapterId,
    @Valid @RequestBody Question question) {
    question.setChapterId(chapterId);
    service.save(question);
  }

  @PutMapping("/teachers/{teacher_id}/chapters/{chapter_id}/questions/{question_id}")
  public void updateQuestion(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("chapter_id") Long chapterId,
    @PathVariable("question_id") Long questionId,
    @Valid @RequestBody Question course) {
    course.setChapterId(chapterId);
    course.setId(questionId);
    service.update(course);
  }

  @DeleteMapping("/teachers/{teacher_id}/chapters/{chapter_id}/questions/{question_id}")
  public void deleteQuestion(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("chapter_id") Long chapterId,
    @PathVariable("question_id") Long questionId) {
    service.deleteById(questionId);
  }

  @GetMapping("/teachers/{teacher_id}/chapters/{chapter_id}/questions")
  public Page<Question> getQuestions(
    @PathVariable("chapter_id") Long chapterId,
    @RequestParam(value = "current_page", required = false, defaultValue = "1") Integer currentPage,
    @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize
  ) {
    Pageable page = PageRequest.of(currentPage - 1, pageSize);
    return service.getQuestionPage(chapterId, page);
  }
}
