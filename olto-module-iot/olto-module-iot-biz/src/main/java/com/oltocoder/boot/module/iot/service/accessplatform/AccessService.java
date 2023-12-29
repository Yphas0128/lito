package com.oltocoder.boot.module.iot.service.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.OnlineExtraPropertyDefine;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接入服务
 */
public interface AccessService {

    /**
     *
     * 设备上线
     *
     * @param accessPlatformName 接入平台名
     * @param deviceId 设备id
     * @param extraProperties 上线所需附加属性列表
     */
    void online(String accessPlatformName, String deviceId, Map<String,String> extraProperties) ;

    /**
     *
     * 设备下线
     *
     * @param deviceId 设备id
     */
    void offline(String deviceId);

    /**
     *
     * 设备上报数据
     *
     * @param deviceId 设备id
     * @param collectTime 采集时间
     * @param data 数据
     */
    void reportData(String deviceId, Date collectTime, Map<String,Object> data);

    /**
     *
     * 获取设备上线所需附加属性
     *
     * @param accessPlatformName
     * @return
     */
    List<OnlineExtraPropertyDefine> getOnlineExtraProperties(String accessPlatformName);

    /**
     *
     * 获取接入平台名列表
     *
     * @return
     */
    List<String> getAccessPlatformNames();
}
