package com.autoexam.apiserver.annotation.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TraceLog {
  String clazz();
  String method();
}