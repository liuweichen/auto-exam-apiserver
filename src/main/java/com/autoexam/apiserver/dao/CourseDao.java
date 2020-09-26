package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseDao extends JpaRepository<Course, Long> {
  @Query("select c from Course c where c.teacherId = :teacherId")
  List<Course> getAllByTeacherId(@Param("teacherId") Long teacherId);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Course c where c.id = :id and c.teacherId = :teacherId")
  void deleteByIdAndTeacherId(@Param("id") Long id, @Param("teacherId") Long teacherId);
}
