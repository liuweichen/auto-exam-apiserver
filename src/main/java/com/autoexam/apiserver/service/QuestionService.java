package com.autoexam.apiserver.service;

import com.autoexam.apiserver.beans.Question;
import com.autoexam.apiserver.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
  @Autowired
  private QuestionDao dao;

  public void save(Question question) {
    dao.save(question);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }

  public Page<Question> getQuestionPage(Long chapterId, Pageable page) {
    return dao.getQuestionPage(chapterId, page);
  }
}
