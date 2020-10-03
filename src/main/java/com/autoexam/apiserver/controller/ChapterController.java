package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Chapter;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.IDJson;
import com.autoexam.apiserver.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ChapterController extends ExceptionHandlerController {
  @Autowired
  private ChapterService service;

  @TraceLog(clazz = "ChapterController", method = "createChapter")
  @PostMapping("/teachers/{teacher_id}/courses/{course_id}/chapters")
  public IDJson createChapter(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("course_id") Long courseId,
    @Valid @RequestBody Chapter chapter) {
    chapter.setCourseId(courseId);
    return service.save(chapter);
  }

  @TraceLog(clazz = "ChapterController", method = "updateChapter")
  @PutMapping("/teachers/{teacher_id}/courses/{course_id}/chapters/{chapter_id}")
  public void updateChapter(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("course_id") Long courseId,
    @PathVariable("chapter_id") Long chapterId,
    @Valid @RequestBody Chapter course) {
    course.setCourseId(courseId);
    course.setId(chapterId);
    service.update(course);
  }

  @GetMapping("/teachers/{teacher_id}/courses/{course_id}/chapters")
  public List<Chapter> getChapters(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("course_id") Long courseId) {
    return service.getAll(courseId);
  }

  @TraceLog(clazz = "ChapterController", method = "deleteChapter")
  @DeleteMapping("/teachers/{teacher_id}/courses/{course_id}/chapters/{chapter_id}")
  public void deleteChapter(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("course_id") Long courseId,
    @PathVariable("chapter_id") Long chapterId) {
    service.deleteById(chapterId);
  }
}
