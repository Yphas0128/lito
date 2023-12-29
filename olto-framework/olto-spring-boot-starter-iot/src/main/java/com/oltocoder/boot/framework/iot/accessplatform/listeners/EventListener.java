package com.oltocoder.boot.framework.iot.accessplatform.listeners;

import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.GetPropertyEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.ServiceInvokeEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetConfigEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetPropertyEvent;

public interface EventListener {

    String getAccessPlatformName();

    void onSetProperty(SetPropertyEvent event);

    void onGetProperty(GetPropertyEvent event);

    void onSetConfig(SetConfigEvent event);

    void onServiceInvoke(ServiceInvokeEvent event);

}
