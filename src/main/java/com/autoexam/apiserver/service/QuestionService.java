package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Question;
import com.autoexam.apiserver.dao.QuestionDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
  @Autowired
  private QuestionDao dao;

  public IDJson save(Question question) {
    return new IDJson(dao.save(question).getId());
  }

  public void update(Question question) {
    Question origin = dao.getOne(question.getId());
    BeanUtil.copyProperties(
      question,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    dao.save(origin);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }

  public Page<Question> getQuestionPage(Long chapterId, Pageable page) {
    return dao.getQuestionPage(chapterId, page);
  }
}
