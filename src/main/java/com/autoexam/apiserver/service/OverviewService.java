package com.autoexam.apiserver.service;

import com.autoexam.apiserver.dao.CourseDao;
import com.autoexam.apiserver.model.response.Overview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverviewService {
  @Autowired
  private CourseDao courseDao;

  public Overview getOverview(Long teacherId) {
    return courseDao.getOverview(teacherId);
  }
}
