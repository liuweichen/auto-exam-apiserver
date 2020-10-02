package com.autoexam.apiserver.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationInfo {
  @ApiModelProperty("签发时间")
  private Long iat;
  @ApiModelProperty("过期时间")
  private Long exp;
  @ApiModelProperty("JWT的ID")
  private String jti;
  @ApiModelProperty("用户名称")
  private String userName;
}
