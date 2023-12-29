package com.oltocoder.boot.framework.iot.accessplatform.listeners.events;

import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.SubscribeCommonPayload;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.ListenerEvent;
import lombok.Data;

import java.util.HashMap;


public class SetPropertyEvent implements ListenerEvent {

    private String deviceId;
    private String method;
    private HashMap<String, Object> properties;

    public SetPropertyEvent(String deviceId, SubscribeCommonPayload payload){
        this.deviceId=deviceId;
        this.method=payload.getMethod();
        this.properties=(HashMap<String, Object>)payload.getParams();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getMethod() {
        return this.method;
    }

    public HashMap<String, Object> getProperties() {
        return this.properties;
    }

}
