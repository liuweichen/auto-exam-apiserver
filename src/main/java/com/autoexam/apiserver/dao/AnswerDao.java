package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AnswerDao extends JpaRepository<Answer, Long> {
  @Query("select a from Answer a where a.questionId in :questionIds")
  List<Answer> getAllByQuestionIds(@Param("questionIds") List<Long> questionIds);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Answer a where a.questionId = :questionId")
  void deleteByQuestionId(@Param("questionId") Long questionId);
}
