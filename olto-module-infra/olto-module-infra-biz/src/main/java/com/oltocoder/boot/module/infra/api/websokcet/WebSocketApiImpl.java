package com.oltocoder.boot.module.infra.api.websokcet;

import com.oltocoder.boot.module.infra.websocket.WebSocketApi;
import com.oltocoder.boot.module.infra.websocket.WebSocketUsers;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class WebSocketApiImpl implements WebSocketApi {


    @Override
    public void sendMessage(Long userId, String message) {
        WebSocketUsers.sendMessage(String.valueOf(userId), message);
    }
}
