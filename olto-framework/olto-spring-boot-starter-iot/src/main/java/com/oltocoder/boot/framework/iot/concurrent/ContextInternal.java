package com.oltocoder.boot.framework.iot.concurrent;

import java.util.concurrent.Executor;

/**
 * @title: ContextInternal
 * @Author cmw
 * @Date: 2023/8/8 16:30
 * @describe
 */
public interface ContextInternal extends Context  {

    @Override
    default void runOnContext(Handler<Void> action) {
        executor().execute(() -> dispatch(action));
    }

    default <T> AsyncPromise<T> promise() {
        return new AsyncPromiseImpl<>(this);
    }

    Executor executor();

    default void dispatch(Handler<Void> handler) {
        dispatch(null, handler);
    }

    default <E> void dispatch(E event, Handler<E> handler) {

        try {
            handler.handle(event);
        } catch (Throwable t) {

        } finally {

        }
    }
}
