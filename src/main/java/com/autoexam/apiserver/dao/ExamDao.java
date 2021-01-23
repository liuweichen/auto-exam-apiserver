package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamDao extends JpaRepository<Exam, Long> {
  @Query("select ex from Exam as ex join Course as co on ex.courseId = co.id where " +
    "(co.teacherId = :teacherId or :teacherId is null) and (ex.courseId = :courseId or :courseId is null) " +
    "and (ex.id = :examId or :examId is null)")
  List<Exam> getWithFilter(
    @Param("teacherId") Long teacherId,
    @Param("courseId") Long courseId,
    @Param("examId") Long examId);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Exam c where c.id = :id and c.courseId = :courseId")
  void deleteByIdAndCourseId(@Param("id") Long id, @Param("courseId") Long courseId);

  @Query("select t from Exam t where t.courseId = :courseId and t.id = :id")
  Optional<Exam> getByCourseIdAndExamId(@Param("courseId") Long courseId, @Param("id") Long id);

  @Query("select count(*) from Exam t where t.courseId = :courseId")
  Integer getCountByCourseId(@Param("courseId") Long courseId);

  @Query("select count(*) from Exam as ex join Course as co on ex.courseId = co.id where co.teacherId = :teacherId")
  Long getCountByTeacherId(@Param("teacherId") Long teacherId);
}
