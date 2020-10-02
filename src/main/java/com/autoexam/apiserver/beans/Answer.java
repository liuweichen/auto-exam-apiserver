package com.autoexam.apiserver.beans;

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
@Table(name = "answer")
public class Answer extends AuditInfo {
  @Id
  @GeneratedValue(generator = "answer_generator")
  @SequenceGenerator(
    name = "answer_generator",
    sequenceName = "answer_sequence",
    initialValue = 1000
  )
  @Column(name = "id", columnDefinition = "bigserial")
  private Long id;

  @NotBlank(message = "content can not be null")
  @Column(name = "content", columnDefinition = "text not null")
  private String content;

  @NotNull(message = "isSelected can not be null")
  @Column(name = "is_selected", columnDefinition = "boolean not null")
  private Boolean isSelected;

  @Column(name = "question_id", columnDefinition = "bigint not null references question(id)")
  private Long questionId;
}
