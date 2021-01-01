package com.autoexam.apiserver.service.common;

import cn.hutool.core.date.DateUtil;
import com.autoexam.apiserver.beans.Teacher;
import com.autoexam.apiserver.dao.TeacherDao;
import com.autoexam.apiserver.model.response.Token;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QiNiuCloudService {
  @Value("${token.cloud.minute:50}")
  private Integer tokenValidTime;
  @Autowired
  private TeacherDao teacherDao;
  private Token cacheToken;

  public Token getUploadToken(Long teacherId) {
    if (cacheToken == null || cacheToken.getExpired() < new Date().getTime()) {
      Teacher teacher = teacherDao.getOne(teacherId);
      Auth auth = Auth.create(teacher.getAk(), teacher.getSk());
      String upToken = auth.uploadToken(teacher.getBucket());
      Date now = new Date();
      Date exp = DateUtil.offsetMinute(now, tokenValidTime);
      cacheToken = new Token(upToken, exp.getTime());
    }
    return cacheToken;
  }
}
