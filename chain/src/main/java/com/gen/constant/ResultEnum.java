package com.gen.constant;

public enum ResultEnum {
    SUCCESS(200),
    ERROR(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public final int code;

    ResultEnum(int code) {
        this.code = code;
    }
}

