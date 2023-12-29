package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeviceOnlineMananer {

    private Map<String, DeviceOnlineData> deviceIdAndOnlineDataMap;
    private Map<String, String> deviceKeyAndDeviceIdMap;

    public DeviceOnlineMananer() {
        this.deviceIdAndOnlineDataMap = new HashMap<>();
        this.deviceKeyAndDeviceIdMap = new HashMap<>();
    }

    public synchronized void addOrUpdate(DeviceOnlineData onlineData) {
        deviceIdAndOnlineDataMap.put(onlineData.getDeviceId(), onlineData);
        deviceKeyAndDeviceIdMap.put(DeviceUtil.getDeviceKey(onlineData.getProductKey(), onlineData.getDeviceName()), onlineData.getDeviceId());
    }

    public synchronized  void remove(String deviceId){
        if(deviceIdAndOnlineDataMap.containsKey(deviceId))
        {
            DeviceOnlineData data=deviceIdAndOnlineDataMap.get(deviceId);
            deviceIdAndOnlineDataMap.remove(deviceId);
            deviceKeyAndDeviceIdMap.remove(DeviceUtil.getDeviceKey(data.getProductKey(),data.getDeviceName()));
        }
    }

    public boolean containDeviceId(String deviceId){
        return deviceIdAndOnlineDataMap.containsKey(deviceId);
    }

    public DeviceOnlineData getData(String deviceId){
        return deviceIdAndOnlineDataMap.get(deviceId);
    }

    public List<DeviceOnlineData> getAllData(){
        return deviceIdAndOnlineDataMap.values().stream().collect(Collectors.toList());
    }

    public String getDeviceId(String deviceKey){
        return deviceKeyAndDeviceIdMap.get(deviceKey);
    }
}
