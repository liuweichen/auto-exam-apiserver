package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Answer;
import com.autoexam.apiserver.beans.Exam;
import com.autoexam.apiserver.beans.Exam2Question;
import com.autoexam.apiserver.beans.Question;
import com.autoexam.apiserver.dao.AnswerDao;
import com.autoexam.apiserver.dao.Exam2QuestionDao;
import com.autoexam.apiserver.dao.ExamDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamService {
  @Autowired
  private ExamDao dao;
  @Autowired
  private Exam2QuestionDao exam2QuestionDao;
  @Autowired
  private AnswerDao answerDao;

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

  public void addQuestion2Exam(Long examId, List<Long> questionIdList) {
    exam2QuestionDao.saveAll(questionIdList.stream().map(id -> new Exam2Question(examId, id)).collect(Collectors.toList()));
  }

  public void removeQuestion2Exam(Long examId, List<Long> questionIdList) {
    exam2QuestionDao.deleteByExamIdAndQuestionIdList(examId, questionIdList);
  }

  public List<Question> getQuestionsByExamId(Long examId) {
    List<Question> res = exam2QuestionDao.getQuestionsByExamId(examId);
    List<Answer> answers = answerDao.getAllByQuestionIds(res.stream().map(q -> q.getId()).collect(Collectors.toList()));
    Map<Long, List<Answer>> answerMap = answers.stream().collect(Collectors.groupingBy(Answer::getQuestionId));
    res.forEach(a -> a.setAnswerList(answerMap.get(a.getId())));
    return res;
  }
}
