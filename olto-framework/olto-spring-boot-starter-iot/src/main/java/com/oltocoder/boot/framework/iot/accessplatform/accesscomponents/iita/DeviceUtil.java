package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

public class DeviceUtil {

    public static String getDeviceKey(String productKey,String deviceName){
        return productKey+"_"+deviceName;
    }
}
