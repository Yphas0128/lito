package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.accessplatform.AccessComponent;
import com.oltocoder.boot.framework.iot.accessplatform.CodecUtil;
import com.oltocoder.boot.framework.iot.accessplatform.HexUtil;
import com.oltocoder.boot.framework.iot.accessplatform.OnlineExtraPropertyDefine;
import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.ReportDataPayload;
import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload.SubDeviceRegisterPayload;
import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformProperties;
import com.oltocoder.boot.framework.iot.accessplatform.config.MqttServerProperties;
import com.oltocoder.boot.framework.iot.accessplatform.data.OnlineData;
import com.oltocoder.boot.framework.iot.accessplatform.data.ReportData;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class IitaPlatformAccessComponent implements AccessComponent {


    private final Vertx vertx = Vertx.vertx();

    private MqttClient mqttClient;

    private AccessPlatformProperties accessPlatformProperties;

    private CountDownLatch connectCountDownLatch;
    private CountDownLatch onlineCountDownLatch;

    private MqttMessageHandler messageHandler;

    private DeviceOnlineMananer deviceOnlineMananer;

    public IitaPlatformAccessComponent() {
        this.deviceOnlineMananer = new DeviceOnlineMananer();
        this.messageHandler = new MqttMessageHandler(this.deviceOnlineMananer);
    }

    @Override
    public boolean isConnected() {
        return mqttClient == null ? false : mqttClient.isConnected();
    }

    @Override
    public void validateProperties(AccessPlatformProperties properties) {

    }

    @Override
    public void setProperties(AccessPlatformProperties properties) {
        this.accessPlatformProperties = properties;

    }

    @Override
    public AccessPlatformProperties getProperties() {
        return this.accessPlatformProperties;
    }

    @Override
    public void start() {
        if (this.accessPlatformProperties == null)
            throw new RuntimeException("properties不能为null");

        connect();

        log.info("accessComponent启动成功 access_platform_name="+this.accessPlatformProperties.getAccessPlatformName()+" access_component_name"+this.accessPlatformProperties.getAccessComponentName());
    }

    @Override
    public void stop() {
        if (!isConnected())
            return;

        offlineAll();

        getClient().disconnect().
                onSuccess(x -> {
                    log.info("mqttClient disconnect ok");
                }).onFailure(x -> {
                    log.info("mqttClient disconnect fail");
                });
    }

    @Override
    public void online(OnlineData data) {

        validateExtraProperties(data.getExtraProperties());

        try {
            onlineCountDownLatch = new CountDownLatch(1);
            DeviceOnlineData onlineData = new DeviceOnlineData(data);

            SubDeviceRegisterPayload messagePayload = new SubDeviceRegisterPayload();
            messagePayload.setId(MqttMessageUtil.getUUID());
            SubDeviceRegisterPayload.BizParams bizParams = new SubDeviceRegisterPayload.BizParams();
            bizParams.setDeviceName(onlineData.getDeviceName());
            bizParams.setProductKey(onlineData.getProductKey());
            bizParams.setModel("m1");
            messagePayload.setParams(bizParams);

            getClient().publish(MqttTopicUtil.doTopicFormat(MqttTopics.PUBLISH_REGISTER, onlineData.getProductKey(), onlineData.getDeviceName()),
                            Buffer.buffer(JsonUtils.toJsonString(messagePayload)), MqttQoS.AT_LEAST_ONCE, false, false)
                    .onSuccess(x -> {
                        Map<String, Integer> topicAndQosMap = getSubscribeTopics(onlineData.getProductKey(), onlineData.getDeviceName());
                        try {
                            getClient().subscribe(topicAndQosMap)
                                    .onSuccess(y -> {
                                        this.deviceOnlineMananer.addOrUpdate(onlineData);
                                        onlineCountDownLatch.countDown();
                                        log.info("成功订阅主题 topicAndQosMap=" + JsonUtils.toJsonString(topicAndQosMap));
                                    })
                                    .onFailure(y -> {
                                        onlineCountDownLatch.countDown();
                                        log.error("订阅主题失败 topicAndQosMap=" + JsonUtils.toJsonString(topicAndQosMap), y.getCause());
                                    });
                        } catch (Exception e) {
                            onlineCountDownLatch.countDown();
                            log.error("订阅主题发生异常", e);
                        }
                    })
                    .onFailure(x -> {
                        onlineCountDownLatch.countDown();
                    });
            onlineCountDownLatch.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void offline(String deviceId) {
        if (!this.deviceOnlineMananer.containDeviceId(deviceId))
            return;

        DeviceOnlineData onlineData = this.deviceOnlineMananer.getData(deviceId);
        List<String> topics = getSubscribeTopics(onlineData.getProductKey(), onlineData.getDeviceName()).keySet().stream()
                .collect(Collectors.toList());
        getClient().unsubscribe(topics);

        this.deviceOnlineMananer.remove(deviceId);
    }


    @Override
    public void reportData(ReportData data) {
        String deviceId = data.getDeviceId();
        if (!this.deviceOnlineMananer.containDeviceId(deviceId))
            return;

        DeviceOnlineData onlineData = this.deviceOnlineMananer.getData(deviceId);
        ReportDataPayload messagePayload = new ReportDataPayload();
        messagePayload.setId(MqttMessageUtil.getUUID());
        messagePayload.setParams(data.getData());
        messagePayload.setOccur(data.getCollectTime().getTime());
        String topicName = MqttTopicUtil.doTopicFormat(MqttTopics.PUBLISH_PROPERTY_POST, onlineData.getProductKey(), onlineData.getDeviceName());
        String payloadJsonString = JsonUtils.toJsonString(messagePayload);
        getClient().publish(topicName,
                        Buffer.buffer(payloadJsonString), MqttQoS.AT_LEAST_ONCE, false, false)
                .onSuccess(x -> {
                    log.info("上报数据成功 topic=" + topicName + " payload=" + payloadJsonString);
                })
                .onFailure(x -> {
                    log.error("上报数据发生异常 topic=" + topicName + " payload=" + payloadJsonString, x.getCause());
                });
    }

    @Override
    public void setListenerFactory(EventListenerFactory listenerFactory) {
        this.messageHandler.setEventListenerFactory(listenerFactory);
    }

    @Override
    public List<OnlineExtraPropertyDefine> getOnlineExtraPropertyDefines() {
        return DeviceExtraPropertyDefines.getExtraPropertyDefines();
    }

    private void offlineAll() {
        List<DeviceOnlineData> allOnlineData = this.deviceOnlineMananer.getAllData();
        allOnlineData.stream().forEach(onlineData -> {
            List<String> topics = getSubscribeTopics(onlineData.getProductKey(), onlineData.getDeviceName()).keySet().stream()
                    .collect(Collectors.toList());
            getClient().unsubscribe(topics);

            this.deviceOnlineMananer.remove(onlineData.getDeviceId());
        });
    }

    private void validateExtraProperties(Map<String, String> extraProperties) {
        DeviceExtraPropertyDefines.getExtraPropertyDefines().stream().filter(x -> x.isRequired())
                .forEach(x -> {
                    if (extraProperties == null)
                        throw new RuntimeException("extraProperties中缺少必填属性");

                    if (!extraProperties.containsKey(x.getPropertyName()))
                        throw new RuntimeException("extraProperties中缺少必填属性[propertyName=" + x.getPropertyName() + "]");
                });
    }

    private MqttClientOptions getOpions(String productKey,String productSecret,String deviceName) {
        String clientId=productKey+"_"+deviceName+"_m1";
        String username=deviceName;
        String password= CodecUtil.md5Str(productSecret+clientId);

        MqttClientOptions mqttClientOptions = new MqttClientOptions();
        mqttClientOptions.setClientId(clientId);
        mqttClientOptions.setUsername(username);
        mqttClientOptions.setPassword(password);
        return mqttClientOptions;
    }

    private MqttClient getClient() {
        if (!this.isConnected()) {
            start();
        }
        return this.mqttClient;
    }

    private Map<String, Integer> getSubscribeTopics(String productKey, String deviceName) {
        Map<String, Integer> topicAndQosMap = new HashMap<>();
        topicAndQosMap.put(MqttTopicUtil.doTopicFormat(MqttTopics.SUBSCRIBE_PROPERTY_GET, productKey, deviceName), 2);
        topicAndQosMap.put(MqttTopicUtil.doTopicFormat(MqttTopics.SUBSCRIBE_PROPERTY_SET, productKey, deviceName), 2);
        topicAndQosMap.put(MqttTopicUtil.doTopicFormat(MqttTopics.SUBSCRIBE_SERVICE_INVOKE, productKey, deviceName), 2);
        topicAndQosMap.put(MqttTopicUtil.doTopicFormat(MqttTopics.SUBSCRIBE_CONFIG_SET, productKey, deviceName), 2);
        return topicAndQosMap;
    }

    private synchronized void connect() {

        if (this.isConnected())
            return;

        try {

            final String deviceId = this.accessPlatformProperties.getUniqueId();
            final String productKey = this.accessPlatformProperties.getExtraProperties().get("product-key");
            final String deviceName = this.accessPlatformProperties.getExtraProperties().get("device-name");
            final String productSecret=this.accessPlatformProperties.getExtraProperties().get("product-secret");

            connectCountDownLatch = new CountDownLatch(1);
            MqttServerProperties mqttServerProperties = this.accessPlatformProperties.getMqttServer();
            MqttClientOptions mqttClientProperties = getOpions(productKey,productSecret,deviceName);
            mqttClient = MqttClient.create(vertx, mqttClientProperties);
            mqttClient.connect(mqttServerProperties.getPort(), mqttServerProperties.getHost())
                    .onSuccess((x) -> {

                        log.info("MqttClient连接成功 Host=" + mqttServerProperties.getHost() + " Port=" + mqttServerProperties.getPort() + "");

                        Map<String, Integer> topicAndQosMap = getSubscribeTopics(productKey, deviceName);

                        try {
                            mqttClient
                                    .subscribe(topicAndQosMap)
                                    .onSuccess(y -> {
                                        DeviceOnlineData onlineData = new DeviceOnlineData();
                                        onlineData.setDeviceId(deviceId);
                                        onlineData.setProductKey(productKey);
                                        onlineData.setDeviceName(deviceName);
                                        this.deviceOnlineMananer.addOrUpdate(onlineData);
                                        connectCountDownLatch.countDown();
                                        log.info("成功订阅主题 topicAndQosMap=" + JsonUtils.toJsonString(topicAndQosMap));
                                    })
                                    .onFailure(y -> {
                                        connectCountDownLatch.countDown();
                                        log.error("订阅主题失败 topicAndQosMap=" + JsonUtils.toJsonString(topicAndQosMap), y.getCause());
                                    });

                            connectCountDownLatch.countDown();
                        } catch (Exception e) {
                            connectCountDownLatch.countDown();
                            log.error("订阅主题发生异常", e);
                        }
                    })
                    .onFailure((z) -> {
                        connectCountDownLatch.countDown();
                        log.error("MqttClient连接失败 Host=" + mqttServerProperties.getHost() + " Port=" + mqttServerProperties.getPort() + "", z.getCause());
                    });
            connectCountDownLatch.await();
            mqttClient.publishHandler(y -> messageHandler.onHandle(y, accessPlatformProperties.getAccessPlatformName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
