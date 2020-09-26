package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ChapterDao extends JpaRepository<Chapter, Long> {
  @Query("select c from Chapter c where c.courseId = :courseId")
  List<Chapter> getAllByCourseId(@Param("courseId") Long courseId);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Chapter c where c.id = :id and c.courseId = :courseId")
  void deleteByIdAndCourseId(@Param("id") Long id, @Param("courseId") Long courseId);
}
