package com.autoexam.apiserver.service.privilege;

import com.autoexam.apiserver.dao.CourseDao;
import com.autoexam.apiserver.dao.TeacherDao;
import com.autoexam.apiserver.exception.BadRequestException;
import com.autoexam.apiserver.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminPrivilegeService {
  @Autowired
  private TeacherDao teacherDao;
  @Autowired
  private CourseDao courseDao;

  public void checkAdminHasTeacher(Long adminId, Long teacherId) {
    teacherDao.getByAdminIdAndTeacherId(adminId, teacherId).orElseThrow(() ->
      new ResourceNotFoundException(String.format("admin: %s does not have teacher: %s", adminId, teacherId)));
  }

  public void checkTeacherNotHasCourses(Long teacherId) {
    int courseCount = courseDao.getAllByTeacherId(teacherId, null).size();
    if (courseCount > 0) {
      throw new BadRequestException(String.format("teacher: %s have total: %d courses", teacherId, courseCount));
    }
  }
}
