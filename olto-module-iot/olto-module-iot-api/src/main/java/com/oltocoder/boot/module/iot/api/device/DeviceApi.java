package com.oltocoder.boot.module.iot.api.device;

import com.oltocoder.boot.module.iot.api.device.dto.DeviceOperatorDTO;

public interface DeviceApi {
    DeviceOperatorDTO getDeviceOperator(String deviceId);
}
