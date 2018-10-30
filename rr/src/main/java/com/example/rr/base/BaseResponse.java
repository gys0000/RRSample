package com.example.rr.base;

import com.example.rr.annotation.KeepNotProgurd;

import java.io.Serializable;

/**
 * {"code": 0,"message": "ok","result": {}}
 * @param <T>
 */
@KeepNotProgurd
public class BaseResponse<T> implements Serializable {
    public int code;
    public String msg;
    public T result;
}
