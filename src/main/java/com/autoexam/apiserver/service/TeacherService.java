package com.autoexam.apiserver.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.dao.TeacherDao;
import com.autoexam.apiserver.model.response.IDJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
  @Autowired
  private TeacherDao dao;

  public IDJson save(Teacher teacher) {
    return new IDJson(dao.save(teacher).getId());
  }

  public void update(Teacher teacher) {
    Teacher origin = dao.getOne(teacher.getId());
    BeanUtil.copyProperties(
      teacher,
      origin,
      CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).ignoreCase());
    dao.save(origin);
  }

  public List<Teacher> getAll(Long adminId) {
    return dao.getAllByAdminId(adminId);
  }

  public List<Teacher> getAll() {
    return dao.findAll();
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}
