package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Course;
import com.autoexam.apiserver.dao.CourseDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
  @Autowired
  private CourseDao dao;

  public IDJson save(Course course) {
    return new IDJson(dao.save(course).getId());
  }

  public void update(Course course) {
    Course origin = dao.getOne(course.getId());
    BeanUtil.copyProperties(
      course,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    dao.save(origin);
  }

  public List<Course> getAll(Long teacherId, Long courseId) {
    return dao.getAllByTeacherId(teacherId, courseId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
