package com.autoexam.apiserver.service.common;

import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.dao.TeacherDao;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QiNiuCloudService {
  @Autowired
  private TeacherDao teacherDao;

  public String getUploadToken(Long teacherId) {
    Teacher teacher = teacherDao.getOne(teacherId);
    Auth auth = Auth.create(teacher.getAk(), teacher.getSk());
    String upToken = auth.uploadToken(teacher.getBucket());
    return upToken;
  }
}
