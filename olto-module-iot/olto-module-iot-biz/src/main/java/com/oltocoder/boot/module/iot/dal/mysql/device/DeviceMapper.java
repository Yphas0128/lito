package com.oltocoder.boot.module.iot.dal.mysql.device;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.iot.controller.admin.device.vo.DevicePageReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceMapper extends BaseMapperX<DeviceDO> {

    //page
    default PageResult<DeviceDO> pageResult(DevicePageReqVO reqVO){
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceDO>().likeIfPresent(DeviceDO::getCode, reqVO.getCode()));
    }

    default DeviceDO getDeviceByCode(String code){
        return selectOne(DeviceDO::getCode, code);
    }

  default DeviceDO getDeviceByCodeNotId(String code, Long id){
      return selectOne(new LambdaQueryWrapperX<DeviceDO>().eq(DeviceDO::getCode, code).ne(DeviceDO::getId,id));
  }

 default  DeviceDO getDeviceByHostAndPort(String host, Integer port){
    return selectOne(new LambdaQueryWrapperX<DeviceDO>().eq(DeviceDO::getHost, host).eq(DeviceDO::getPort, port));
 }
}
