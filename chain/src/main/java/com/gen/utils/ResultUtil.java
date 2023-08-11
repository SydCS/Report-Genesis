package com.gen.utils;

import com.gen.constant.ResultEnum;
import com.gen.vo.Result;

public class ResultUtil {
    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnum.SUCCESS);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS).setData(data);
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<T>();
        result.setCode(ResultEnum.ERROR).setMsg(msg);
        return result;
    }

    public static <T> Result<T> unauthorized(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ResultEnum.UNAUTHORIZED).setMsg(msg);
        return result;
    }

    public static <T> Result<T> define(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code).setMsg(msg).setData(data);
        return result;
    }

}
