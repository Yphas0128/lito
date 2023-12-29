package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

import java.util.UUID;

public class MqttMessageUtil {
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }
}
