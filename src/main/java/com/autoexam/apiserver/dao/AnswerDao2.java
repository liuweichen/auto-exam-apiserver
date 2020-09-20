package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Answer2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnswerDao2 extends JpaRepository<Answer2, Long> {
    List<Answer2> findByQuestionId(Long questionId);
}
