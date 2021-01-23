package com.autoexam.apiserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherOverview {
  private Long courseCount;
  private Long chapterCount;
  private Long questionCount;
  private Long examCount;

  public TeacherOverview(Long courseCount, Long chapterCount, Long questionCount) {
    this.courseCount = courseCount;
    this.chapterCount = chapterCount;
    this.questionCount = questionCount;
  }
}
