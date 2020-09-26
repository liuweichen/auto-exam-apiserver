package com.autoexam.apiserver.service;

import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
  @Autowired
  private TeacherDao dao;

  public void save(Teacher teacher) {
    dao.save(teacher);
  }

  public List<Teacher> getAll(Long adminId) {
    return dao.getAllByAdminId(adminId);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
