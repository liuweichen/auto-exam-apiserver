package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Question;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.IDJson;
import com.autoexam.apiserver.service.QuestionService;
import com.autoexam.apiserver.service.privilege.TeacherPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController extends ExceptionHandlerController {
  @Autowired
  private QuestionService service;
  @Autowired
  private TeacherPrivilegeService privilegeService;

  @TraceLog(clazz = "QuestionController", method = "createQuestion")
  @PostMapping("/teachers/{teacher_id}/questions")
  public IDJson createQuestion(
    @PathVariable("teacher_id") Long teacherId,
    @Valid @RequestBody Question question) {
    privilegeService.checkTeacherHasChapter(teacherId, question.getChapterId());
    return service.save(question);
  }

  @TraceLog(clazz = "QuestionController", method = "updateQuestion")
  @PutMapping("/teachers/{teacher_id}/questions/{question_id}")
  public void updateQuestion(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("question_id") Long questionId,
    @Valid @RequestBody Question question) {
    privilegeService.checkTeacherHasQuestion(teacherId, questionId);
    if (!StringUtils.isEmpty(question.getChapterId())) {
      privilegeService.checkTeacherHasChapter(teacherId, question.getChapterId());
    }
    question.setId(questionId);
    service.update(question);
  }

  @TraceLog(clazz = "QuestionController", method = "deleteQuestion")
  @DeleteMapping("/teachers/{teacher_id}/questions/{question_id}")
  public void deleteQuestion(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("question_id") Long questionId) {
    privilegeService.checkTeacherHasQuestion(teacherId, questionId);
    service.deleteById(questionId);
  }

  @TraceLog(clazz = "QuestionController", method = "deleteQuestionBash")
  @PostMapping("/teachers/{teacher_id}/questions/delete")
  public void deleteQuestionBash(
    @PathVariable("teacher_id") Long teacherId,
    @RequestBody List<Long> idList) {
    privilegeService.checkTeacherHasQuestionList(teacherId, idList);
    service.deleteByIdList(idList);
  }

  @GetMapping("/teachers/{teacher_id}/questions")
  public Page<Question> getQuestions(
    @PathVariable("teacher_id") Long teacherId,
    @RequestParam(value = "course_id", required = false) Long courseId,
    @RequestParam(value = "chapter_id", required = false) Long chapterId,
    @RequestParam(value = "question_id", required = false) Long questionId,
    @RequestParam(value = "type", required = false) Integer type,
    @RequestParam(value = "current_page", required = false, defaultValue = "1") Integer currentPage,
    @RequestParam(value = "page_size", required = false, defaultValue = "20") Integer pageSize,
    @RequestParam(value = "sort_field", required = false, defaultValue = "createdAt") String sortField,
    @RequestParam(value = "sort_order", required = false, defaultValue = "desc") String sortOrder
  ) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
    Pageable page = PageRequest.of(currentPage - 1, pageSize, sort);
    return service.getQuestionPage(teacherId, courseId, chapterId,  questionId, type, page);
  }
}
