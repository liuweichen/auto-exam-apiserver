package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
  @Query("select t from Student t where t.name = :name")
  Optional<Student> getOneByName(@Param("name") String name);
}
