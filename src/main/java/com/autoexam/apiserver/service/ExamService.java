package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Exam;
import com.autoexam.apiserver.dao.ExamDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {
  @Autowired
  private ExamDao dao;

  public IDJson save(Exam exam) {
    return new IDJson(dao.save(exam).getId());
  }

  public void update(Exam exam) {
    Exam origin = dao.getOne(exam.getId());
    BeanUtil.copyProperties(
      exam,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    dao.save(origin);
  }

  public List<Exam> getAll(Long teacherId, Long courseId, Long examId) {
    return dao.getWithFilter(teacherId, courseId, examId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
