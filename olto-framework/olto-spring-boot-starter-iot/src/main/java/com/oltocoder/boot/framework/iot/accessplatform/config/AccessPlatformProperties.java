package com.oltocoder.boot.framework.iot.accessplatform.config;

import lombok.Data;

import java.util.Map;

@Data
public class AccessPlatformProperties {

    private String accessPlatformName;

    private String accessComponentName;

    private MqttServerProperties mqttServer;

    private Map<String, String> extraProperties;

    public String getUniqueId(){
        return (this.accessPlatformName+"_"+this.accessComponentName).toLowerCase();
    }
}