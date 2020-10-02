package com.autoexam.apiserver.dao;

import com.autoexam.apiserver.beans.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Long> {
  @Query("select t from Teacher t where t.adminId = :adminId")
  List<Teacher> getAllByAdminId(@Param("adminId") Long adminId);

  @Modifying
  @Transactional(rollbackFor = Exception.class)
  @Query("delete from Teacher t where t.id = :id and t.adminId = :adminId")
  void deleteByIdAndAdminId(@Param("id") Long id, @Param("adminId") Long adminId);

  @Query("select t from Teacher t where t.name = :name")
  Optional<Teacher> getOneByName(@Param("name") String name);
}
