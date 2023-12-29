package com.oltocoder.boot.module.iot.dal.mysql.point;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointPageReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PointMapper extends BaseMapperX<PointDO> {
   default PageResult<PointDO> pageResult(PointPageReqVO reqVO){
       return selectPage(reqVO, new LambdaQueryWrapperX<PointDO>()
               .eqIfPresent(PointDO::getDeviceId,reqVO.getDeviceId()));
   }

    default List<PointDO> getPointsByDeviceId(Long deviceId){
       return selectList(PointDO::getDeviceId, deviceId);
    }
}
