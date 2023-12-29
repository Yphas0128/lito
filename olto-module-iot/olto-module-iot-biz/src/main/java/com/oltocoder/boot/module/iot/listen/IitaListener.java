package com.oltocoder.boot.module.iot.listen;

import com.oltocoder.boot.framework.common.enums.ModbusTypeEnum;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.GetPropertyEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.ServiceInvokeEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetConfigEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetPropertyEvent;
import com.oltocoder.boot.framework.iot.client.modbus.ModBusTcpClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.AsyncCallback;
import com.oltocoder.boot.framework.iot.concurrent.AsyncFuture;
import com.oltocoder.boot.framework.iot.dal.ModBusTcpBaseEntity;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.oltocoder.boot.module.iot.service.accessplatform.AccessService;
import com.oltocoder.boot.module.iot.service.device.DeviceService;
import com.oltocoder.boot.module.iot.service.point.PointService;
import com.iteaj.iot.FrameworkComponent;
import com.iteaj.iot.client.SocketClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.iteaj.iot.event.ClientStatusEventListener;

@Component
public class IitaListener implements EventListener, ClientStatusEventListener {

    private static final String IitaName = "iita";
    @Resource
    private AccessService accessService;

    @Resource(name = "deviceService")
    private DeviceService deviceService;

    @Resource(name = "pointService")
    private PointService pointService;

    @Resource
    private ModBusTcpClientSystem modBusTcpClientSystem;

    @Override
    public String getAccessPlatformName() {
        return IitaName;
    }

    @Override
    public void onSetProperty(SetPropertyEvent event) {

        DeviceDO deviceDO = deviceService.getDeviceByCode(event.getDeviceId());

        ModBusTcpBaseEntity entity = new ModBusTcpBaseEntity();
        entity.setDeviceSn(deviceDO.getCode());
        entity.setPort(deviceDO.getPort());
        entity.setIp(deviceDO.getHost());
        List<PointDO> points = pointService.getPointsByDeviceId(deviceDO.getId());

        for (PointDO pointDO: points) {
            if(!event.getProperties().keySet().contains(pointDO.getProperty())) continue;
            String value = event.getProperties().get(pointDO.getProperty()).toString();
            entity.setFieldType(pointDO.getFieldType());
            entity.setStartAddress(Integer.valueOf(pointDO.getAddress()));
            entity.setNum(pointDO.getNum());
            entity.setCmd(ModbusTypeEnum.valueOf(pointDO.getModbusType()).getCode());
            entity.setWriteValue(value);
            AsyncFuture<String> asyncFuture = modBusTcpClientSystem.performRead(entity);
//            asyncFuture.onComplete(new AsyncCallback<String>() {
//                @Override
//                public void onSuccess(String result) {
//                    Map<String, Object> data = new HashMap();
//                    data.put(pointDO.getProperty() ,result);
//                    accessService.reportData(event.getDeviceId(), new Date(), data);
//                }
//
//                @Override
//                public void onFailure(Throwable throwable) {
//
//                }
//            });
        }
    }

    @Override
    public void onGetProperty(GetPropertyEvent event) {

        DeviceDO deviceDO = deviceService.getDeviceByCode(event.getDeviceId());
        ModBusTcpBaseEntity entity = new ModBusTcpBaseEntity();
        entity.setDeviceSn(deviceDO.getCode());
        entity.setPort(deviceDO.getPort());
        entity.setIp(deviceDO.getHost());
        List<PointDO> points = pointService.getPointsByDeviceId(deviceDO.getId());


        for (PointDO pointDO: points) {
            if(!event.getPropertyNames().contains(pointDO.getProperty())) continue;
            entity.setFieldType(pointDO.getFieldType());
            entity.setStartAddress(Integer.valueOf(pointDO.getAddress()));
            entity.setNum(pointDO.getNum());
            entity.setCmd(ModbusTypeEnum.valueOf(pointDO.getModbusType()).getCode());
            AsyncFuture<String> asyncFuture = modBusTcpClientSystem.performRead(entity);
            asyncFuture.onComplete(new AsyncCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Map<String, Object> data = new HashMap();
                    data.put(pointDO.getProperty() ,result);
                    accessService.reportData(event.getDeviceId(), new Date(), data);
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }
    }

    @Override
    public void onSetConfig(SetConfigEvent event) {

    }

    @Override
    public void onServiceInvoke(ServiceInvokeEvent event) {

    }

    //上线 server
    @Override
    public void online(Object source, FrameworkComponent component) {
        SocketClient client = (SocketClient) source;
        DeviceDO deviceDO =  deviceService.getDeviceByHostAndPort(client.getConfig().getLocalHost(),client.getConfig().getLocalPort());

        Map<String, String> data = new HashMap<>();
        data.put("product-key", deviceDO.getName());
        data.put("device-name", String.format("%s_%s", IitaName,deviceDO.getId()));

        accessService.online(null,deviceDO.getCode(), data);
    }



    //
    @Override
    public void offline(Object source, FrameworkComponent component) {
        SocketClient client = (SocketClient) source;
        DeviceDO deviceDO =  deviceService.getDeviceByHostAndPort(client.getConfig().getLocalHost(),client.getConfig().getLocalPort());

        accessService.offline(deviceDO.getCode());
    }
}
