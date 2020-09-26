package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
  @Query("select s from Question s where s.chapterId = :chapterId")
  Page<Question> getQuestionPage(@Param("chapterId") Long chapterId, Pageable page);
}
