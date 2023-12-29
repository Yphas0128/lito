package com.oltocoder.boot.framework.iot.concurrent;

/**
 * @title: AsyncCallback
 * @Author cmw
 * @Date: 2023/8/8 15:53
 * @describe
 */
public interface AsyncCallback<T> {
    void onSuccess(T result);
    void onFailure(Throwable throwable);
}
