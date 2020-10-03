package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Answer;
import com.autoexam.apiserver.dao.AnswerDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
  @Autowired
  private AnswerDao dao;

  public IDJson save(Answer answer) {
    return new IDJson(dao.save(answer).getId());
  }

  public void update(Answer chapter) {
    Answer origin = dao.getOne(chapter.getId());
    BeanUtil.copyProperties(
      chapter,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    dao.save(origin);
  }

  public List<Answer> getAll(Long questionId) {
    return dao.getAllByQuestionId(questionId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
