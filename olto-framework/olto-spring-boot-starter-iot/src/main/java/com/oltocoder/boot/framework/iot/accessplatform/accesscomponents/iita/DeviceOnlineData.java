package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

import com.oltocoder.boot.framework.iot.accessplatform.data.OnlineData;
import lombok.Data;

@Data
public class DeviceOnlineData {

    private String accessPlatformName;
    private String deviceId;
    private String productKey;
    private String deviceName;

    public DeviceOnlineData(OnlineData data) {
        this.accessPlatformName = data.getAccessPlatformName();
        this.deviceId = data.getDeviceId();
        this.deviceName = data.getExtraPropertyValue("device-name","");
        this.productKey = data.getExtraPropertyValue("product-key","");
    }

    public DeviceOnlineData(){}
}
