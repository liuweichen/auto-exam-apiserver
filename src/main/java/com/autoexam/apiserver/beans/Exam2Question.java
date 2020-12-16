package com.autoexam.apiserver.beans;

import com.autoexam.apiserver.beans.base.AuditInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(Exam2QuestionUPK.class)
@Table(name = "exam2question")
public class Exam2Question extends AuditInfo implements Serializable {
  @Id
  @Column(name = "examId", columnDefinition = "bigint not null references exam(id)")
  private Long examId;

  @Id
  @Column(name = "questionId", columnDefinition = "bigint not null references question(id)")
  private Long questionId;
}

class Exam2QuestionUPK implements Serializable {
  private Long examId;
  private Long questionId;
}
