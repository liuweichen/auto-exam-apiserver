package com.autoexam.apiserver.beans;

import com.autoexam.apiserver.annotation.file.PostNotNull;
import com.autoexam.apiserver.beans.base.AuditInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question extends AuditInfo {
  @Id
  @GeneratedValue(generator = "question_generator")
  @SequenceGenerator(
    name = "question_generator",
    sequenceName = "question_sequence",
    initialValue = 1000
  )
  @Column(name = "id", columnDefinition = "bigserial")
  private Long id;

  @NotNull
  @Column(name = "type", columnDefinition = "int not null")
  private Integer type;

  @NotBlank
  @Column(name = "content", columnDefinition = "text not null")
  private String content;

  @Column(name = "explanation", columnDefinition = "text")
  private String explanation;

  @PostNotNull
  @Column(name = "chapter_id", columnDefinition = "bigint not null references chapter(id)")
  private Long chapterId;
}
