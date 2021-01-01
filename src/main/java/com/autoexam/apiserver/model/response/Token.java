package com.autoexam.apiserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
  public Token(String token) {
    this.token = token;
  }
  private String token;
  private Long expired;
}
