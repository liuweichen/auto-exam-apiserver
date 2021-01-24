package com.autoexam.apiserver.model.request;

import com.autoexam.apiserver.beans.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(
  value = {"password", "newPassword"},
  allowSetters = true
)
public class UpdateTeacher extends Teacher {
  @NotNull
  @Size(min = 3, max = 128, message = "new password length should be between 3 and 128")
  private String newPassword;
}
