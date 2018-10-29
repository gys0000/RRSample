package com.example.rr.exception;

public class RequestExpiredException extends APIException {
    public RequestExpiredException(String message, Throwable cause, int code, String message1) {
        super(message, cause, code, message1);
    }
}
