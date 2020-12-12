package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
  @Query("select q from Question as q join Chapter as ch on q.chapterId = ch.id join Course as co on ch.courseId = co.id " +
    " where (co.teacherId = :teacherId or :teacherId is null) and (co.id = :courseId or :courseId is null) " +
    " and (ch.id = :chapterId or :chapterId is null) and (q.id = :questionId or :questionId is null) " +
    " and (q.type = :type or :type is null)")
  Page<Question> getPageWithFilter(
    @Param("teacherId") Long teacherId,
    @Param("courseId") Long courseId,
    @Param("chapterId") Long chapterId,
    @Param("questionId") Long questionId,
    @Param("type") Integer type,
    Pageable page);

  @Query("select t from Question t where t.chapterId = :chapterId and t.id = :id")
  Optional<Question> getByChapterIdAndQuestionId(@Param("chapterId") Long chapterId, @Param("id") Long id);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Question a where a.id in :idList")
  void deleteByQuestionIdList(@Param("idList") List<Long> idList);
}
