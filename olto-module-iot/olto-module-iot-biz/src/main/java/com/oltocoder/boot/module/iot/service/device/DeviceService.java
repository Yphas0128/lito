package com.oltocoder.boot.module.iot.service.device;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.common.util.collection.CollectionUtils;
import com.oltocoder.boot.module.iot.api.device.dto.DeviceComponentDTO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DevicePageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DeviceService {
    Long createDevice(DeviceCreateReqVO reqVO);

    int deleteDevice(Long id);

    int updateDevice(DeviceUpdateReqVO reqVO);

    DeviceDO getDevice(Long id);

    DeviceDO getDeviceByCode(String code);

    DeviceDO getDeviceByCodeNotId(String code, Long id);

    PageResult<DeviceDO> getDevicePage(DevicePageReqVO reqVO);

    List<DeviceDO> getDeviceList();

    int updateDeviceState(DeviceDO device);

    void runDevice(Long id);

    ProtocolDO getDeviceProtocol(Long deviceId);

    List<DeviceComponentDTO> getDeviceComponents(List<Long> ids);

    List<DeviceDO> getDevices(List<Long> ids);

    default Map<Long, DeviceDO> getDeviceMap(List<Long> ids){
        if(CollUtil.isEmpty(ids))
            return new HashMap<>();
        List<DeviceDO> devices = getDevices(ids);
        return CollectionUtils.convertMap(devices, DeviceDO::getId);
    }

    DeviceDO getDeviceByHostAndPort(String host, Integer port);
}
