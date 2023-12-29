package com.oltocoder.boot.framework.iot.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformProperties;
import com.oltocoder.boot.framework.iot.accessplatform.data.OnlineData;
import com.oltocoder.boot.framework.iot.accessplatform.data.ReportData;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;

import java.util.List;

public interface AccessComponent {
    void validateProperties(AccessPlatformProperties properties);

    void setProperties(AccessPlatformProperties properties);

    AccessPlatformProperties getProperties();

    void start();
    void stop();
    boolean isConnected();
    void online(OnlineData data);
    void offline(String  deviceId);
    void reportData(ReportData data);
    void setListenerFactory(EventListenerFactory listenerFactory);

    List<OnlineExtraPropertyDefine> getOnlineExtraPropertyDefines();

}
