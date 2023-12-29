package com.oltocoder.boot.framework.iot.accessplatform.listeners.events;

import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.SubscribeCommonPayload;
import lombok.Data;

import java.util.HashMap;


public class SetConfigEvent {
    private String deviceId;
    private String method;
    private HashMap<String,Object> configData;

    public SetConfigEvent(String deviceId, SubscribeCommonPayload payload){
        this.deviceId=deviceId;
        this.method=payload.getMethod();
        this.configData= (HashMap<String, Object>)payload.getParams();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getMethod() {
        return this.method;
    }

    public HashMap<String, Object> getConfigData() {
        return this.configData;
    }

}
