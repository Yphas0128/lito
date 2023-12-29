package com.oltocoder.boot.framework.iot.client;

import com.oltocoder.boot.framework.iot.concurrent.AsyncFuture;



/**
 * @title: ClientSystem
 * @Author cmw
 * @Date: 2023/8/9 19:31
 * @describe
 */
public interface ClientSystem<R> {
     AsyncFuture performRead(R params);
     AsyncFuture performWrite(R params);

}
