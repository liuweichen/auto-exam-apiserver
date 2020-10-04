package com.autoexam.apiserver.service.privilege;

import com.autoexam.apiserver.dao.TeacherDao;
import com.autoexam.apiserver.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPrivilegeService {
  @Autowired
  private TeacherDao teacherDao;

  public void checkAdminHasTeacher(Long adminId, Long teacherId) {
    teacherDao.getByAdminIdAndTeacherId(adminId, teacherId).orElseThrow(() ->
      new AuthenticationException(String.format("admin: %s does not have teacher: %s", adminId, teacherId)));
  }
}
