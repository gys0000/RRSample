package com.example.rr.exception;

public class APIException extends RuntimeException {
    private int code;
    private String message;

    public APIException(String message, Throwable cause, int code, String message1) {
        this.code = code;
        this.message = message1;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
