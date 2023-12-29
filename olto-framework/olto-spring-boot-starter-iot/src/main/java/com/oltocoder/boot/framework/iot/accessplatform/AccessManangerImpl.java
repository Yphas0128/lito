package com.oltocoder.boot.framework.iot.accessplatform;


import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformListProperties;
import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformProperties;
import com.oltocoder.boot.framework.iot.accessplatform.data.OnlineData;
import com.oltocoder.boot.framework.iot.accessplatform.data.ReportData;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AccessManangerImpl implements AccessMananger, DisposableBean {

    private String defaultAccessPlatformName;

    private Map<String, AccessPlatformProperties> accessPlatformNameAndPropertiesMap;

    private Map<String, AccessComponent> accessPlatformNameAndAccessComponentMap;

    private ConcurrentHashMap<String, OnlineData> deviceIdAndOnlineDataMap;

    private AccessComponentFactory accessComponentFactory;

    private EventListenerFactory eventListenerFactory;

    private AccessPlatformListProperties properties;


    @Autowired
    public AccessManangerImpl(AccessComponentFactory accessComponentFactory, EventListenerFactory eventListenerFactory, AccessPlatformListProperties properties) {
        this.deviceIdAndOnlineDataMap = new ConcurrentHashMap<>();
        this.accessPlatformNameAndAccessComponentMap = new HashMap<>();

        this.accessComponentFactory = accessComponentFactory;
        this.eventListenerFactory = eventListenerFactory;
        this.properties = properties;
    }

    @PostConstruct
    public void initAndStartMananger() {

        this.init(properties);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
        thread.setPriority(Thread.NORM_PRIORITY);
        thread.start();
    }

    @Override
    public void init(AccessPlatformListProperties properties) {

        this.accessPlatformNameAndPropertiesMap = properties.getAccessPlatforms()
                .values()
                .stream()
                .collect(Collectors.toMap((x) -> x.getAccessPlatformName(), (y) -> y));

        List<AccessPlatformProperties> values = properties.getAccessPlatforms().values().stream().collect(Collectors.toList());
        if (values.isEmpty()) {
            log.warn("接入网关配置列表不能为空");
            return;
        }
        for (AccessPlatformProperties value : values) {
            AccessComponent accessComponent = this.accessComponentFactory.getComponent(value.getUniqueId(), value.getAccessComponentName());
            accessComponent.validateProperties(value);
            accessComponent.setProperties(value);
            accessComponent.setListenerFactory(this.eventListenerFactory);
            this.accessPlatformNameAndAccessComponentMap.put(value.getAccessPlatformName(), accessComponent);
        }
        this.defaultAccessPlatformName = values.get(0).getAccessPlatformName();
    }

    @Override
    public void start() {
        this.accessPlatformNameAndAccessComponentMap.values().forEach(accessComponent -> {
            AccessPlatformProperties properties = accessComponent.getProperties();
            try {
                accessComponent.start();
            } catch (Exception e) {
                log.warn("AccessComponent start失败 gatewayName[" + properties.getAccessPlatformName() + "] accessComponent[name=" + properties.getAccessComponentName() + "]");
            }
        });
    }

    @Override
    public void online(String deviceId, String accessPlatformName, Map<String, String> extraProperties) {
        if (isOnline(deviceId))
            return;

        if (accessPlatformName == null || accessPlatformName.isEmpty())
            accessPlatformName = defaultAccessPlatformName;

        AccessComponent component = getComponentByGatewayName(accessPlatformName);

        OnlineData onlineData = OnlineData.builder()
                .deviceId(deviceId)
                .extraProperties(extraProperties)
                .accessPlatformName(accessPlatformName)
                .build();
        component.online(onlineData);

        this.deviceIdAndOnlineDataMap.put(deviceId, onlineData);
    }

    @Override
    public void offline(String deviceId) {
        if (!isOnline(deviceId))
            return;

        getComponent(deviceId)
                .offline(deviceId);

        removeOnline(deviceId);
    }

    @Override
    public void reportData(String deviceId, Date collectTime, Map<String, Object> data) {
        if (!isOnline(deviceId))
            return;

        ReportData reportData = ReportData.builder()
                .deviceId(deviceId)
                .collectTime(collectTime)
                .data(data)
                .build();
        getComponent(deviceId).reportData(reportData);
    }


    @Override
    public List<OnlineExtraPropertyDefine> getOnlineExtraPropertyDefines(String accessPlatformName) {
        if (accessPlatformName == null || accessPlatformName.isEmpty())
            accessPlatformName = defaultAccessPlatformName;
        AccessComponent component = getComponentByGatewayName(accessPlatformName);
        if (component == null)
            return null;
        return component.getOnlineExtraPropertyDefines();
    }

    @Override
    public List<String> getAccessPlatformNames() {
        return this.accessPlatformNameAndPropertiesMap.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public void stop() {
        this.accessPlatformNameAndAccessComponentMap.values().forEach(accessComponent -> {
            AccessPlatformProperties properties = accessComponent.getProperties();
            try {
                accessComponent.stop();
            } catch (Exception e) {
                throw new RuntimeException("AccessComponent stop失败 gatewayName[" + properties.getAccessPlatformName() + "] accessComponent[name=" + properties.getAccessComponentName() + "]", e);
            }
        });
    }

    @Override
    public void destroy() throws Exception {
        stop();
    }

    private boolean isOnline(String deviceId) {
        return this.deviceIdAndOnlineDataMap.containsKey(deviceId);
    }

    private AccessComponent getComponent(String deviceId) {
        OnlineData onlineData = this.deviceIdAndOnlineDataMap.get(deviceId);
        return getComponentByGatewayName(onlineData.getAccessPlatformName());
    }

    private AccessComponent getComponentByGatewayName(String gatewayName) {
        return this.accessPlatformNameAndAccessComponentMap.get(gatewayName);
    }

    private void removeOnline(String deviceId) {
        this.deviceIdAndOnlineDataMap.remove(deviceId);
    }


}
