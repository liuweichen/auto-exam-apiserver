package com.autoexam.apiserver.beans;

import com.autoexam.apiserver.beans.base.AuditInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
public class Course extends AuditInfo {
  @Id
  @GeneratedValue(generator = "course_generator")
  @SequenceGenerator(
    name = "course_generator",
    sequenceName = "course_sequence",
    initialValue = 1000
  )
  @Column(name = "id", columnDefinition = "bigserial")
  private Long id;

  @NotBlank
  @Size(min = 3, max = 128, message = "name length should be between 3 and 128")
  @Column(name = "name", columnDefinition = "text not null")
  private String name;

  @Column(name = "description", columnDefinition = "text")
  private String description;

  @Column(name = "teacher_id", columnDefinition = "bigint not null references teacher(id)")
  private Long teacherId;
}
