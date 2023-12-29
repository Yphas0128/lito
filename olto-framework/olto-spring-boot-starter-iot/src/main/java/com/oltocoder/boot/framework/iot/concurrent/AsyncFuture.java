package com.oltocoder.boot.framework.iot.concurrent;

/**
 * @title: AsyncFuture
 * @Author cmw
 * @Date: 2023/8/8 15:54
 * @describe
 */
public interface AsyncFuture<T> {

    void onComplete(AsyncCallback<T> callback);
}
