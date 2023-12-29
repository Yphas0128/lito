package com.oltocoder.boot.framework.iot.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: AsyncPromiseImpl
 * @Author cmw
 * @Date: 2023/8/8 15:55
 * @describe
 */
public class AsyncPromiseImpl<T> implements AsyncPromise<T> {
    private List<AsyncCallback<T>> callbacks = new ArrayList<>();
    protected final ContextInternal context;
    public AsyncPromiseImpl(ContextInternal context) {
        this.context = context;
    }

    @Override
    public void onComplete(AsyncCallback<T> callback) {
        callbacks.add(callback);
    }

    @Override
    public void setSuccess(T result) {
        for (AsyncCallback<T> callback : callbacks) {
            callback.onSuccess(result);
        }
    }

    @Override
    public void setFailure(Throwable throwable) {
        for (AsyncCallback<T> callback : callbacks) {
            callback.onFailure(throwable);
        }
    }
}
