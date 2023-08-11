package com.gen.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户登录请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    private String name;
    private String password;
}
