package com.example.rr.exception;

/**
 * 未登录造成的异常
 */
public class UnLoginException extends APIException {
    public UnLoginException(String message, Throwable cause, int code, String message1) {
        super(message, cause, code, message1);
    }
}
