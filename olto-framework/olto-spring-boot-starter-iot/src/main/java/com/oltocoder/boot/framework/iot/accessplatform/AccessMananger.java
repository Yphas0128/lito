package com.oltocoder.boot.framework.iot.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformListProperties;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AccessMananger {
     void init(AccessPlatformListProperties properties);
    void start();
    void stop();

    void online(String deviceId, String gatewayName, Map<String, String> extraProperties);

    void offline(String deviceId);

    void reportData(String deviceId, Date collectTime, Map<String, Object> data);


    List<OnlineExtraPropertyDefine> getOnlineExtraPropertyDefines(String accessPlatformName);

    List<String> getAccessPlatformNames();
}
