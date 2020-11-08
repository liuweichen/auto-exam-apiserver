package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChapterDao extends JpaRepository<Chapter, Long> {
  @Query("select ch from Chapter as ch join Course as co on ch.courseId = co.id where " +
    "(co.teacherId = :teacherId or :teacherId is null) and (ch.courseId = :courseId or :courseId is null) " +
    "and (ch.id = :chapterId or :chapterId is null)")
  List<Chapter> getWithFilter(
    @Param("teacherId") Long teacherId,
    @Param("courseId") Long courseId,
    @Param("chapterId") Long chapterId);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Chapter c where c.id = :id and c.courseId = :courseId")
  void deleteByIdAndCourseId(@Param("id") Long id, @Param("courseId") Long courseId);

  @Query("select t from Chapter t where t.courseId = :courseId and t.id = :id")
  Optional<Chapter> getByCourseIdAndChapterId(@Param("courseId") Long courseId, @Param("id") Long id);
}
