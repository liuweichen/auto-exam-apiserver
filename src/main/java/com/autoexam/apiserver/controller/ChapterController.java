package com.autoexam.apiserver.controller;

import com.autoexam.apiserver.annotation.log.TraceLog;
import com.autoexam.apiserver.beans.Chapter;
import com.autoexam.apiserver.controller.base.ExceptionHandlerController;
import com.autoexam.apiserver.model.response.IDJson;
import com.autoexam.apiserver.service.ChapterService;
import com.autoexam.apiserver.service.privilege.TeacherPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ChapterController extends ExceptionHandlerController {
  @Autowired
  private ChapterService service;
  @Autowired
  private TeacherPrivilegeService privilegeService;

  @TraceLog(clazz = "ChapterController", method = "createChapter")
  @PostMapping("/teachers/{teacher_id}/chapters")
  public IDJson createChapter(
    @PathVariable("teacher_id") Long teacherId,
    @Valid @RequestBody Chapter chapter) {
    privilegeService.checkTeacherHasCourse(teacherId, chapter.getCourseId());
    return service.save(chapter);
  }

  @TraceLog(clazz = "ChapterController", method = "updateChapter")
  @PutMapping("/teachers/{teacher_id}/chapters/{chapter_id}")
  public void updateChapter(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("chapter_id") Long chapterId,
    @Valid @RequestBody Chapter chapter) {
    privilegeService.checkTeacherHasChapter(teacherId, chapterId);
    if (!StringUtils.isEmpty(chapter.getCourseId())) {
      privilegeService.checkTeacherHasCourse(teacherId, chapter.getCourseId());
    }
    chapter.setId(chapterId);
    service.update(chapter);
  }

  @GetMapping("/teachers/{teacher_id}/chapters")
  public List<Chapter> getChapters(
    @PathVariable("teacher_id") Long teacherId,
    @RequestParam("course_id") Long courseId) {
    return service.getAll(courseId);
  }

  @TraceLog(clazz = "ChapterController", method = "deleteChapter")
  @DeleteMapping("/teachers/{teacher_id}/chapters/{chapter_id}")
  public void deleteChapter(
    @PathVariable("teacher_id") Long teacherId,
    @PathVariable("chapter_id") Long chapterId) {
    privilegeService.checkTeacherHasChapter(teacherId, chapterId);
    service.deleteById(chapterId);
  }
}
