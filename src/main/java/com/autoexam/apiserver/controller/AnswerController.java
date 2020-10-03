package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Answer;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.IDJson;
import com.autoexam.apiserver.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AnswerController extends ExceptionHandlerController {
  @Autowired
  private AnswerService service;

  @TraceLog(clazz = "AnswerController", method = "createAnswer")
  @PostMapping("/teachers/{teacher_id}/questions/{question_id}/answers")
  public IDJson createAnswer(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("question_id") Long questionId,
    @Valid @RequestBody Answer answer) {
    answer.setQuestionId(questionId);
    return service.save(answer);
  }

  @TraceLog(clazz = "AnswerController", method = "updateAnswer")
  @PutMapping("/teachers/{teacher_id}/questions/{question_id}/answers/{answer_id}")
  public void updateAnswer(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("question_id") Long questionId,
    @PathVariable("answer_id") Long answerId,
    @Valid @RequestBody Answer answer) {
    answer.setQuestionId(questionId);
    answer.setId(answerId);
    service.update(answer);
  }

  @TraceLog(clazz = "AnswerController", method = "deleteAnswer")
  @DeleteMapping("/teachers/{teacher_id}/questions/{question_id}/answers/{answer_id}")
  public void deleteAnswer(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("question_id") Long questionId,
    @PathVariable("answer_id") Long answerId) {
    service.deleteById(answerId);
  }

  @GetMapping("/teachers/{teacher_id}/questions/{question_id}/answers")
  public List<Answer> getAnswers(@PathVariable("question_id") Long answerId) {
    return service.getAll(answerId);
  }
}
