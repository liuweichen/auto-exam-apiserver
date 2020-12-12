package com.autoexam.apiserver.service.privilege;

import com.autoexam.apiserver.dao.CourseDao;
import com.autoexam.apiserver.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherPrivilegeService {
  @Autowired
  private CourseDao courseDao;

  public void checkTeacherHasCourse(Long teacherId, Long courseId) {
    courseDao.getByTeacherIdAndCourseId(teacherId, courseId).orElseThrow(() ->
      new AuthenticationException(String.format("teacher: %s does not have course: %s", teacherId, courseId)));
  }

  public void checkTeacherHasChapter(Long teacherId, Long chapterId) {
    courseDao.getByTeacherIdAndChapterId(teacherId, chapterId).orElseThrow(() ->
      new AuthenticationException(String.format("teacher: %s does not have chapter: %s", teacherId, chapterId)));
  }

  public void checkTeacherHasQuestion(Long teacherId, Long questionId) {
    courseDao.getByTeacherIdAndQuestionId(teacherId, questionId).orElseThrow(() ->
      new AuthenticationException(String.format("teacher: %s does not have question: %s", teacherId, questionId)));
  }

  public void checkTeacherHasQuestionList(Long teacherId, List<Long> idList) {
    if (idList.size() != courseDao.getByTeacherIdAndQuestionIdList(teacherId, idList)) {
      throw new AuthenticationException(
        String.format("teacher: %s does not have question: %s", teacherId, idList.toString()));
    }
  }
}
