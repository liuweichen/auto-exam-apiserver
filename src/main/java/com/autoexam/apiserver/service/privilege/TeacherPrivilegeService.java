package com.autoexam.apiserver.service.privilege;

import com.autoexam.apiserver.dao.ChapterDao;
import com.autoexam.apiserver.dao.CourseDao;
import com.autoexam.apiserver.dao.ExamDao;
import com.autoexam.apiserver.dao.QuestionDao;
import com.autoexam.apiserver.exception.BadRequestException;
import com.autoexam.apiserver.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherPrivilegeService {
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private ChapterDao chapterDao;
  @Autowired
  private QuestionDao questionDao;
  @Autowired
  private ExamDao examDao;

  public void checkTeacherHasCourse(Long teacherId, Long courseId) {
    courseDao.getByTeacherIdAndCourseId(teacherId, courseId).orElseThrow(() ->
      new ResourceNotFoundException(String.format("teacher: %s does not have course: %s", teacherId, courseId)));
  }

  public void checkTeacherHasChapter(Long teacherId, Long chapterId) {
    courseDao.getByTeacherIdAndChapterId(teacherId, chapterId).orElseThrow(() ->
      new ResourceNotFoundException(String.format("teacher: %s does not have chapter: %s", teacherId, chapterId)));
  }

  public void checkTeacherHasExam(Long teacherId, Long examId) {
    courseDao.getByTeacherIdAndExamId(teacherId, examId).orElseThrow(() ->
      new ResourceNotFoundException(String.format("teacher: %s does not have exam: %s", teacherId, examId)));
  }

  public void checkTeacherHasQuestion(Long teacherId, Long questionId) {
    courseDao.getByTeacherIdAndQuestionId(teacherId, questionId).orElseThrow(() ->
      new ResourceNotFoundException(String.format("teacher: %s does not have question: %s", teacherId, questionId)));
  }

  public void checkTeacherHasQuestionList(Long teacherId, List<Long> idList) {
    if (idList.size() != courseDao.getByTeacherIdAndQuestionIdList(teacherId, idList)) {
      throw new ResourceNotFoundException(
        String.format("teacher: %s does not have question: %s", teacherId, idList.toString()));
    }
  }

  public void checkCourseNotHasChapters(Long courseId) {
    int chapterCount = chapterDao.getCountByCourseId(courseId);
    if (chapterCount > 0) {
      throw new BadRequestException(String.format("course: %s have total: %d chapter", courseId, chapterCount));
    }
  }

  public void checkChapterNotHasQuestions(Long chapterId) {
    int questionCount = questionDao.getCountByChapterId(chapterId);
    if (questionCount > 0) {
      throw new BadRequestException(String.format("chapter: %s have total: %d questions", chapterId, questionCount));
    }
  }

  public void checkExamNotHasQuestions(Long chapterId) {
    // TODO: get question count in exam
    int questionCount = 0;
    if (questionCount > 0) {
      throw new BadRequestException(String.format("chapter: %s have total: %d questions", chapterId, questionCount));
    }
  }
}
