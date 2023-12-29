package com.oltocoder.boot.module.iot.convert.device;

import com.oltocoder.boot.module.iot.api.device.dto.DeviceComponentDTO;
import com.oltocoder.boot.module.iot.config.dto.DeviceDTO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceItemRespVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceRespVO;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DeviceUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface DeviceConvert {

    DeviceConvert INSTANCE = Mappers.getMapper(DeviceConvert.class);

    DeviceDO convert(DeviceCreateReqVO reqVO);

    DeviceDO convert01(DeviceUpdateReqVO reqVO);

    DeviceRespVO convert02(DeviceDO device);

    DeviceItemRespVO convert03(DeviceDO deviceDO);

    List<DeviceRespVO> convertList(List<DeviceDO> devices);

    DeviceDTO convertDTO(DeviceDO device);

    DeviceComponentDTO convertComponentDTO(DeviceDO deviceDO);
}
