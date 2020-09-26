package com.autoexam.apiserver.service;

import com.autoexam.apiserver.beans.Chapter;
import com.autoexam.apiserver.dao.ChapterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService {
  @Autowired
  private ChapterDao dao;

  public void save(Chapter course) {
    dao.save(course);
  }

  public List<Chapter> getAll(Long courseId) {
    return dao.getAllByCourseId(courseId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
