package com.oltocoder.boot.framework.iot.accessplatform.config;

import lombok.Data;

@Data
public  class MqttServerProperties {
    private String host;
    private int port;
    private String clientId;
    private String username;
    private String password;
}
