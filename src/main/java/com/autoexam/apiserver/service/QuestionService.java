package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Answer;
import com.autoexam.apiserver.beans.Question;
import com.autoexam.apiserver.dao.AnswerDao;
import com.autoexam.apiserver.dao.QuestionDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionService {
  @Autowired
  private QuestionDao questionDao;
  @Autowired
  private AnswerDao answerDao;

  @Transactional(rollbackFor = Exception.class)
  public IDJson save(Question question) {
    long questionId = questionDao.save(question).getId();
    question.getAnswerList().forEach(a -> a.setQuestionId(questionId));
    answerDao.saveAll(question.getAnswerList());
    return new IDJson(questionId);
  }

  @Transactional(rollbackFor = Exception.class)
  public void update(Question question) {
    long questionId = question.getId();
    Question origin = questionDao.getOne(questionId);
    origin.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    BeanUtil.copyProperties(
      question,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    questionDao.save(origin);
    answerDao.deleteByQuestionId(questionId);
    List<Answer> answerList = question.getAnswerList();
    if (answerList != null && !answerList.isEmpty()) {
      answerList.forEach(q -> q.setQuestionId(questionId));
      answerDao.saveAll(answerList);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteById(Long id) {
    answerDao.deleteByQuestionId(id);
    questionDao.deleteById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  public void deleteByIdList(List<Long> idList) {
    answerDao.deleteByQuestionIdList(idList);
    questionDao.deleteByQuestionIdList(idList);
  }

  public Page<Question> getQuestionPage(
    Long teacherId,
    Long courseId,
    Long chapterId,
    Long questionId,
    Integer type,
    Pageable page) {
    Page<Question> res = questionDao.getPageWithFilter(teacherId, courseId, chapterId, questionId, type, page);
    List<Answer> answers = answerDao.getAllByQuestionIds(res.get().map(q -> q.getId()).collect(Collectors.toList()));
    Map<Long, List<Answer>> answerMap = answers.stream().collect(Collectors.groupingBy(Answer::getQuestionId));
    res.forEach(a -> a.setAnswerList(answerMap.get(a.getId())));
    return res;
  }
}
