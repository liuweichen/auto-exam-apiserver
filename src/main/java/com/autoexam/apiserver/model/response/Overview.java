package com.autoexam.apiserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Overview {
  private Long teacherCount;
  private Long courseCount;
  private Long chapterCount;
  private Long questionCount;
  private Long examCount;
}
