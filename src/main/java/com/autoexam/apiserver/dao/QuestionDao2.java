package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Question2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao2 extends JpaRepository<Question2, Long> {
}
