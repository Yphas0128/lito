package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

public interface MqttTopics {
    String SUBSCRIBE_SERVICE_INVOKE = "/sys/{}/{}/c/service/+";

    String SUBSCRIBE_CONFIG_SET = "/sys/{}/{}/c/config/set";
    String SUBSCRIBE_PROPERTY_SET = "/sys/{}/{}/c/service/property/set";
    String SUBSCRIBE_PROPERTY_GET = "/sys/{}/{}/c/service/property/get";
    String PUBLISH_PROPERTY_POST = "/sys/{}/{}/s/event/property/post";
    String PUBLISH_REGISTER = "/sys/{}/{}/s/register";
}