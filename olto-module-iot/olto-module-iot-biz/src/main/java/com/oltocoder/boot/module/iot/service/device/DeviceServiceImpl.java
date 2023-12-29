package com.oltocoder.boot.module.iot.service.device;

import cn.hutool.core.util.ObjUtil;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.iot.core.consts.DeviceStatus;
import com.oltocoder.boot.module.iot.api.device.dto.DeviceComponentDTO;
import com.oltocoder.boot.module.iot.config.ComponentFactoryAdapter;
import com.oltocoder.boot.module.iot.config.dto.DeviceDTO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DevicePageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceUpdateReqVO;
import com.oltocoder.boot.module.iot.convert.device.DeviceConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;
import com.oltocoder.boot.module.iot.dal.mysql.device.DeviceMapper;
import com.oltocoder.boot.module.iot.service.protocol.ProtocolService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.oltocoder.boot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.oltocoder.boot.framework.common.util.collection.CollectionUtils.convertList;
import static com.oltocoder.boot.module.system.enums.ErrorCodeConstants.DEVICE_IS_EXISTS;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    @Resource
    public DeviceMapper deviceMapper;

    @Resource(name = "protocolService")
    private ProtocolService protocolService;
    @Resource
    private ComponentFactoryAdapter componentFactoryAdapter;
    
    @Override
    public Long createDevice(DeviceCreateReqVO reqVO) {
        DeviceDO deviceDO = getDeviceByCode(reqVO.getCode());
        if(ObjUtil.isNotNull(deviceDO))
            throw exception(DEVICE_IS_EXISTS);
        deviceDO = DeviceConvert.INSTANCE.convert(reqVO);
        int count = deviceMapper.insert(deviceDO);
        return count>0 ? deviceDO.getId() : 0L ;
    }

    @Override
    public int updateDevice(DeviceUpdateReqVO reqVO) {
        DeviceDO deviceDO = getDeviceByCodeNotId(reqVO.getCode(),reqVO.getId());
        if(ObjUtil.isNotNull(deviceDO))
            throw exception(DEVICE_IS_EXISTS);
        deviceDO = DeviceConvert.INSTANCE.convert01(reqVO);
        return deviceMapper.updateById(deviceDO);
    }


    @Override
    public int deleteDevice(Long id) {
        return deviceMapper.deleteById(id);
    }

    @Override
    public DeviceDO getDevice(Long id) {
        return deviceMapper.selectById(id);
    }

    @Override
    public  DeviceDO getDeviceByCodeNotId(String code, Long id) {
        return deviceMapper.getDeviceByCodeNotId(code,id);
    }

    @Override
    public DeviceDO getDeviceByCode(String code) {
        return deviceMapper.getDeviceByCode(code);
    }
    @Override
    public PageResult<DeviceDO> getDevicePage(DevicePageReqVO reqVO) {
        return deviceMapper.pageResult(reqVO);
    }

    @Override
    public List<DeviceDO> getDeviceList() {
        return deviceMapper.selectList();
    }

    @Override
    public int updateDeviceState(DeviceDO device) {
        return deviceMapper.updateById(device);
    }

    @Override
    public void runDevice(Long id) {
        DeviceDO device = getDevice(id);
        ProtocolDO protocol = protocolService.getProtocol(device.getProtocol());
        DeviceDTO deviceDTO = DeviceConvert.INSTANCE.convertDTO(device);
        deviceDTO.setComponentName(protocol.getComponentName());
        componentFactoryAdapter.createAndConnect(deviceDTO);
        device.setState(DeviceStatus.online.ordinal());
        updateDeviceState(device);
    }

    @Override
    public ProtocolDO getDeviceProtocol(Long id) {
        DeviceDO device = getDevice(id);
        if(ObjUtil.isNull(device))
            return null;
        return protocolService.getProtocol(device.getProtocol());
    }

    @Override
    public List<DeviceComponentDTO> getDeviceComponents(List<Long> ids) {
        List<DeviceDO> devices =  getDevices(ids);
        List<Long> protocolIds = convertList(devices, DeviceDO::getProtocol);
        Map<Long, ProtocolDO> protocolMap = protocolService.getProtocolMap(protocolIds);

        List<DeviceComponentDTO> result = devices.stream().map(deviceDO -> {
            DeviceComponentDTO deviceComponentDTO = DeviceConvert.INSTANCE.convertComponentDTO(deviceDO);
            Optional.ofNullable(protocolMap.get(deviceDO.getProtocol())).ifPresent(protocolDO -> deviceComponentDTO.setComponentName(protocolDO.getComponentName()));
            return deviceComponentDTO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<DeviceDO> getDevices(List<Long> ids) {
        return deviceMapper.selectBatchIds(ids);
    }

    @Override
    public DeviceDO getDeviceByHostAndPort(String host, Integer port) {
        return deviceMapper.getDeviceByHostAndPort(host, port);
    }
}
