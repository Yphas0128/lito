package com.oltocoder.boot.module.iot.job;

import com.oltocoder.boot.framework.common.enums.ModbusTypeEnum;
import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.client.modbus.ModBusTcpClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.AsyncCallback;
import com.oltocoder.boot.framework.iot.concurrent.AsyncFuture;
import com.oltocoder.boot.framework.iot.dal.ModBusTcpBaseEntity;
import com.oltocoder.boot.framework.quartz.core.handler.JobHandler;
import com.oltocoder.boot.framework.tenant.core.job.TenantJob;
import com.oltocoder.boot.module.iot.service.accessplatform.AccessService;
import com.oltocoder.boot.module.iot.service.collectorjob.dto.JobDeviceDTO;
import com.oltocoder.boot.module.iot.service.device.DeviceService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//CollectorJobHandler
@Component
@Scope("prototype") // 多列
@TenantJob
public class CollectorJobHandler implements JobHandler {

    @Resource
    private ModBusTcpClientSystem modBusTcpClientSystem;


    @Resource
    private AccessService accessService;
    /**
     *  不同频率下的不同采集任务集合
     */
    @Override
    public String execute(String params) throws Exception {
        List<JobDeviceDTO> devices = JsonUtils.parseArray(params, JobDeviceDTO.class);
         // 具体逻辑
        for (JobDeviceDTO jobDevice: devices) {
            ModBusTcpBaseEntity entity = new ModBusTcpBaseEntity();
            entity.setDeviceSn(jobDevice.getCode());
            entity.setPort(jobDevice.getPort());
            entity.setIp(jobDevice.getHost());
            for (JobDeviceDTO.JobPointDTO jobPointDTO:jobDevice.getPoints()) {
                entity.setCmd(ModbusTypeEnum.valueOf(jobPointDTO.getModbusType()).getCode());
                entity.setFieldType(jobPointDTO.getFieldType());
                entity.setStartAddress(Integer.valueOf(jobPointDTO.getAddress()));
                entity.setNum(jobPointDTO.getNum());
                AsyncFuture<String> asyncFuture = modBusTcpClientSystem.performRead(entity);
                Map<String,Object> data = new HashMap<>();
                asyncFuture.onComplete(new AsyncCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        data.put(jobPointDTO.getProperty(),result);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {

                    }

                });

                accessService.reportData(jobDevice.getCode(), new Date(),data);
            }
        }
        return null;
    }
}
