package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Exam2Question;
import com.autoexam.apiserver.beans.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Exam2QuestionDao extends JpaRepository<Exam2Question, Long> {
  @Query("select count(*) from Exam2Question t where t.examId = :examId")
  Integer getCountByExamId(@Param("examId") Long examId);

  @Query("select count(*) from Exam2Question t where t.questionId in :questionIdList")
  Integer getCountByQuestionIdList(@Param("questionIdList") List<Long> questionIdList);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Exam2Question c where c.examId = :examId and c.questionId in :questionIdList")
  void deleteByExamIdAndQuestionIdList(@Param("examId") Long examId, @Param("questionIdList") List<Long> questionIdList);

  @Query("select q from Exam2Question t join Question q on t.questionId = q.id where t.examId = :examId")
  List<Question> getQuestionsByExamId(@Param("examId") Long examId);
}
