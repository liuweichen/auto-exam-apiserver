package com.autoexam.apiserver.service.common;

import cn.hutool.core.date.DateUtil;
import com.autoexam.apiserver.beans.Admin;
import com.autoexam.apiserver.dao.AdminDao;
import com.autoexam.apiserver.dao.QuestionDao;
import com.autoexam.apiserver.dao.TeacherDao;
import com.autoexam.apiserver.model.response.Token;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class QiNiuCloudService {
  @Value("${token.cloud.minute:50}")
  private Integer tokenValidTime;
  @Autowired
  private AdminDao adminDao;
  @Autowired
  private TeacherDao teacherDao;
  @Autowired
  private QuestionDao questionDao;
  private Token cacheToken;

  public Token getUploadToken(Long teacherId) {
    if (cacheToken == null || cacheToken.getExpired() < new Date().getTime()) {
      Admin admin = adminDao.getOne(teacherDao.getOne(teacherId).getAdminId());
      Auth auth = Auth.create(admin.getAk(), admin.getSk());
      String upToken = auth.uploadToken(admin.getBucket());
      Date now = new Date();
      Date exp = DateUtil.offsetMinute(now, tokenValidTime);
      cacheToken = new Token(upToken, exp.getTime());
    }
    return cacheToken;
  }

  public void deleteImageBash(Long teacherId, List<Long> questionIdList) {
    List<String> imageUrls = questionDao.getImageUrlByQuestionIdList(questionIdList);
    if (!imageUrls.isEmpty()) {
      Configuration cfg = new Configuration(Region.region2());
      Admin admin = adminDao.getOne(teacherDao.getOne(teacherId).getAdminId());
      Auth auth = Auth.create(admin.getAk(), admin.getSk());
      BucketManager bucketManager = new BucketManager(auth, cfg);
      try {
        //单次批量请求的文件数量不得超过1000
        String[] keyList = imageUrls.toArray(new String[imageUrls.size()]);;
        BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
        batchOperations.addDeleteOp(admin.getBucket(), keyList);
        Response response = bucketManager.batch(batchOperations);
        BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
        for (int i = 0; i < keyList.length; i++) {
          BatchStatus status = batchStatusList[i];
          if (status.code != 200) {
            log.error("delete image file failed: " + status.toString());
          }
        }
      } catch (QiniuException ex) {
        throw new RuntimeException("catch exception in delete file", ex);
      }
    }
  }
}
