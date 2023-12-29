package com.oltocoder.boot.framework.iot.accessplatform.listeners.events;

import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.SubscribeCommonPayload;

import java.util.HashMap;

public class ServiceInvokeEvent {
    private String deviceId;
    private String method;
    private HashMap<String, Object> params;

    public ServiceInvokeEvent(String deviceId, SubscribeCommonPayload payload){
        this.deviceId=deviceId;
        this.method=payload.getMethod();
        this.params=(HashMap<String, Object> )payload.getParams();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getMethod() {
        return this.method;
    }

    public HashMap<String, Object> getParams() {
        return this.params;
    }

}
