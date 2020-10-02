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
@Table(name = "student")
public class Student extends AuditInfo {
  @Id
  @GeneratedValue(generator = "student_generator")
  @SequenceGenerator(
    name = "student_generator",
    sequenceName = "student_sequence",
    initialValue = 1000
  )
  @Column(name = "id", columnDefinition = "bigserial")
  private Long id;

  @NotBlank
  @Size(min = 3, max = 128, message = "name length should be between 3 and 128")
  @Column(name = "name", columnDefinition = "text not null unique")
  private String name;

  @NotBlank
  @Size(min = 3, max = 128, message = "password length should be between 3 and 128")
  @Column(name = "password", columnDefinition = "text not null")
  private String password;

  @Column(name = "description", columnDefinition = "text")
  private String description;
}
