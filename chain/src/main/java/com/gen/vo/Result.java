package com.gen.vo;

import com.gen.constant.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@ApiModel(description = "统一请求的返回对象")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    @ApiModelProperty("状态码")
    private int code;
    @ApiModelProperty("消息")
    private String msg;
    @ApiModelProperty("数据")
    private T data;

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public Result setCode(ResultEnum resultEnum) {
        this.code = resultEnum.code;
        return this;
    }
}


