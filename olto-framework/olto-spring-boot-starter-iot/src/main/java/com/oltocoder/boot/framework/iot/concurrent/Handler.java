package com.oltocoder.boot.framework.iot.concurrent;

/**
 * @title: Handler
 * @Author cmw
 * @Date: 2023/8/8 16:19
 * @describe
 */
public interface Handler<E> {

    /**
     * Something has happened, so handle it.
     *
     * @param event  the event to handle
     */
    void handle(E event);
}
