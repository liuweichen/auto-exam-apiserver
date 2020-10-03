package com.autoexam.apiserver.annotation.log;

import com.alibaba.fastjson.JSON;
import com.autoexam.apiserver.annotation.log.TraceLog;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Slf4j
public class TraceLogSupport {
  @Pointcut("@annotation(com.autoexam.apiserver.annotation.log.TraceLog)")
  private void pointcut() {
  }

  @Before("pointcut()&&@annotation(traceLog)")
  public void before(JoinPoint joinPoint, TraceLog traceLog) {
    Object[] args = joinPoint.getArgs();
    log.info(generateLog(traceLog, JSON.toJSONString(args)));
  }

  private String generateLog(TraceLog traceLog, String args) {
    List<String> elements = ImmutableList.of(
      traceLog.clazz(),
      traceLog.method(),
      args
    );
    return String.join("; ", elements);
  }
}