package com.gen.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户注册请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    private String name;
    private String password;
    @ApiModelProperty("验证码")
    private String code;
}