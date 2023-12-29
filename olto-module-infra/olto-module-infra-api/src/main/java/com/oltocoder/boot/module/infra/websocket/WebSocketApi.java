package com.oltocoder.boot.module.infra.websocket;

public interface WebSocketApi {

    void sendMessage(Long userId, String message);
}
