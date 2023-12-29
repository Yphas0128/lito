package com.oltocoder.boot.framework.iot.client;

import com.oltocoder.boot.framework.iot.client.ClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.ContextInternal;
import com.oltocoder.boot.framework.iot.concurrent.Handler;
import com.oltocoder.boot.framework.iot.dal.BaseEntity;
import com.oltocoder.boot.framework.iot.concurrent.AsyncFuture;
import com.oltocoder.boot.framework.iot.concurrent.AsyncPromise;

/**
 * @title: CollectAction
 * @Author cmw
 * @Date: 2023/8/8 16:20
 * @describe
 */
public abstract class AbstractClientSystem<R extends BaseEntity,T> implements ClientSystem<R> {

    protected final ContextInternal context;

    protected AbstractClientSystem(ContextInternal context) {
        this.context = context;
    }


    /**
     * 策略执行读
     * @param params
     * @return
     */
    @Override
    public AsyncFuture<T> performRead(R params) {
        return new BlockingAction<T>(){
            @Override
            public T perform() throws Exception {
                return readInternal(params);
            }
        }.run();
    }

    /**
     * 策略执行写
     * @param params
     * @return
     */
    @Override
    public AsyncFuture<T> performWrite(R params) {
        return new BlockingAction<T>(){
            @Override
            public T perform() throws Exception {
                return writeInternal(params);
            }
        }.run();
    }


    /**
     * 读取数据
     * @param params
     * @return
     * @throws Exception
     */
    protected abstract T readInternal(R params) throws Exception;

    /**
     * 写入数据
     * @param params
     * @return
     * @throws Exception
     */
    protected abstract T writeInternal(R params) throws Exception;


    /**
     * 异步执行Action
     * @param <T>
     */
    protected abstract class BlockingAction<T> implements Handler<AsyncPromise<T>> {

        public AsyncFuture<T> run() {
            return context.executeBlocking(this);
        }
        @Override
        public void handle(AsyncPromise<T> event) {
            try {
                T result = perform();
                event.setSuccess(result);
            } catch (Exception e) {
                event.setFailure(e);
            }
        }

        public abstract T perform() throws Exception;

    }
}
