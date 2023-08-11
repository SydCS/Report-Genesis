package com.gen.exception;

import com.gen.utils.ResultUtil;
import com.gen.vo.Result;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class) //捕获所有异常
    public Result handler(Exception e) {
        e.printStackTrace();
        return ResultUtil.error(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class) //捕获Shiro异常
    public Result handler(ShiroException e) {
        e.printStackTrace();
        return ResultUtil.unauthorized(e.getMessage());
    }

}
