package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
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

  public void update(Chapter chapter) {
    Chapter origin = dao.getOne(chapter.getId());
    BeanUtil.copyProperties(
      chapter,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    dao.save(origin);
  }

  public List<Chapter> getAll(Long courseId) {
    return dao.getAllByCourseId(courseId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
