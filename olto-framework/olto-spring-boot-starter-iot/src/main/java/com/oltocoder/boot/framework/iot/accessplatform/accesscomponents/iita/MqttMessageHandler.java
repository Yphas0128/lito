package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;


import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.SubscribeCommonPayload;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.GetPropertyEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.ServiceInvokeEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetConfigEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetPropertyEvent;
import io.vertx.mqtt.messages.MqttPublishMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;

@Slf4j
public class MqttMessageHandler {
    private DeviceOnlineMananer deviceOnlineMananger;

    private EventListenerFactory listenerFactory;

    private final ExecutorService executorService = new ThreadPoolExecutor(16, 64,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    public MqttMessageHandler(DeviceOnlineMananer deviceOnlineMananger) {
        this.deviceOnlineMananger = deviceOnlineMananger;
    }

    public void setEventListenerFactory(EventListenerFactory listenerFactory) {
        this.listenerFactory = listenerFactory;
    }


    public void onHandle(MqttPublishMessage message,String accessPlatformName) {
        log.info("onHandle topic=" + message.topicName() + " message payload=" + message.payload().toString());

        EventListener eventListener=this.listenerFactory.getListener(accessPlatformName);
        if (eventListener == null)
            return;

        executorService.submit(() -> {
            List<String> topicResolveItems = resolveTopicName(message.topicName());
            String deviceKey = DeviceUtil.getDeviceKey(topicResolveItems.get(0), topicResolveItems.get(1));
            String deviceId = this.deviceOnlineMananger.getDeviceId(deviceKey);
            if (deviceId == null)
                return;

            SubscribeCommonPayload payload = JsonUtils.parseObject(message.payload().toString(), SubscribeCommonPayload.class);
            triggerEvent(eventListener, deviceKey, deviceId, topicResolveItems.get(2), topicResolveItems.get(3), payload);
        });
    }

    private void triggerEvent(EventListener eventListener, String deviceKey, String deviceId, String actionType, String actionName, SubscribeCommonPayload payload) {
        if (actionType.equals("/c/config")) {
            if (actionName.equals("/set")) {
                SetConfigEvent event = new SetConfigEvent(deviceId,payload);
                eventListener.onSetConfig(event);
            }
        } else if (actionType.equals("/c/service")) {
            if (actionName.equals("/property/set")) {
                SetPropertyEvent event = new SetPropertyEvent(deviceId,payload);
                eventListener.onSetProperty(event);
            } else if (actionName.equals("/property/get")) {
                GetPropertyEvent event = new GetPropertyEvent(deviceId,payload);
                eventListener.onGetProperty(event);
            } else {
                ServiceInvokeEvent event = new ServiceInvokeEvent(deviceId,payload);
                eventListener.onServiceInvoke(event);
            }
        }

    }

    private List<String> resolveTopicName(String topicName) {
        List<String> items = new ArrayList<>();
        String[] topicNameSplits = topicName.split("/");
        String productKey = topicNameSplits[2];
        String deviceName = topicNameSplits[3];

        String actionType = "";
        String actionName = "";
        for (int i = 4; i < topicNameSplits.length; i++) {
            if (i < 6) {
                actionType += "/" + topicNameSplits[i];
            } else {
                actionName += "/" + topicNameSplits[i];
            }
        }

        items.add(productKey);
        items.add(deviceName);
        items.add(actionType);
        items.add(actionName);

        return items;
    }
}
