package com.oltocoder.boot.framework.iot.concurrent;

/**
 * @title: AsyncPromise
 * @Author cmw
 * @Date: 2023/8/8 15:54
 * @describe
 */
public interface AsyncPromise<T> extends AsyncFuture<T> {

    void setSuccess(T result);
    void setFailure(Throwable throwable);
}
