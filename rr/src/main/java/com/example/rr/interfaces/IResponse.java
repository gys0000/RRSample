package com.example.rr.interfaces;

/**
 * 网络请求的回调
 *
 * @param <T>
 */
public interface IResponse<T> {
    void onSuccess(T baseModel);

    void onError(Throwable e);
}
