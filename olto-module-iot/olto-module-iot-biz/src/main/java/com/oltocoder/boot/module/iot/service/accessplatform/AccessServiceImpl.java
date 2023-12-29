package com.oltocoder.boot.module.iot.service.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.AccessMananger;
import com.oltocoder.boot.framework.iot.accessplatform.OnlineExtraPropertyDefine;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AccessServiceImpl implements AccessService {

    private AccessMananger accessMananger;


    @Autowired
    public AccessServiceImpl(AccessMananger accessMananger){
        this.accessMananger=accessMananger;
    }

    @Override
    public void online(String accessPlatformName, String deviceId, Map<String, String> extraProperties)  {
        accessMananger.online(deviceId, accessPlatformName, extraProperties);
    }


    @Override
    public void offline(String deviceId) {
        accessMananger.offline(deviceId);
    }

    @Override
    public void reportData(String deviceId, Date collectTime, Map<String, Object> data) {
        accessMananger.reportData(deviceId, collectTime, data);
    }

    @Override
    public List<OnlineExtraPropertyDefine> getOnlineExtraProperties(String gatewayName) {
        return accessMananger.getOnlineExtraPropertyDefines(gatewayName);
    }

    @Override
    public List<String> getAccessPlatformNames() {
        return accessMananger.getAccessPlatformNames();
    }


}
