package com.autoexam.apiserver.service;

import com.autoexam.apiserver.dao.CourseDao;
import com.autoexam.apiserver.dao.ExamDao;
import com.autoexam.apiserver.model.response.TeacherOverview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverviewService {
  @Autowired
  private CourseDao courseDao;
  @Autowired
  private ExamDao examDao;

  public TeacherOverview getOverview(long teacherId) {
    TeacherOverview overview = courseDao.getTeacherOverview(teacherId);
    overview.setExamCount(examDao.getCountByTeacherId(teacherId));
    return overview;
  }
}
