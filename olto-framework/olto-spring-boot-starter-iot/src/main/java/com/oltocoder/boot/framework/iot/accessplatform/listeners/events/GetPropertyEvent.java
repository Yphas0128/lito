package com.oltocoder.boot.framework.iot.accessplatform.listeners.events;

import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.SubscribeCommonPayload;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.ListenerEvent;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GetPropertyEvent implements ListenerEvent {
    private String deviceId;
    private  String method;
    private List<String> propertyNames;

    public GetPropertyEvent(String deviceId, SubscribeCommonPayload payload){
     this.deviceId=deviceId;
     this.method=payload.getMethod();
     this.propertyNames=(ArrayList<String>)payload.getParams();
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getMethod() {
        return this.method;
    }

    public List<String> getPropertyNames() {
        return this.propertyNames;
    }


}
