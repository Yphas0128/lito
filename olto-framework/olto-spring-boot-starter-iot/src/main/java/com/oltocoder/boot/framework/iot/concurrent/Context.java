package com.oltocoder.boot.framework.iot.concurrent;

/**
 * @title: Context
 * @Author cmw
 * @Date: 2023/8/8 16:29
 * @describe
 */
public interface Context {

    void runOnContext(Handler<Void> action);

    <T> AsyncFuture<T> executeBlocking(Handler<AsyncPromise<T>> blockingCodeHandler);
}
