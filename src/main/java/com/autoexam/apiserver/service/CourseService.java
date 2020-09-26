package com.autoexam.apiserver.service;

import com.autoexam.apiserver.beans.Course;
import com.autoexam.apiserver.dao.CourseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
  @Autowired
  private CourseDao dao;

  public void save(Course course) {
    dao.save(course);
  }

  public List<Course> getAll(Long teacherId) {
    return dao.getAllByTeacherId(teacherId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
