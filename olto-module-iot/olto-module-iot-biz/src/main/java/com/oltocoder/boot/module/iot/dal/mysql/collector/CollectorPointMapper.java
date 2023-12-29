package com.oltocoder.boot.module.iot.dal.mysql.collector;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;

import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointPageReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorPointDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectorPointMapper extends BaseMapperX<CollectorPointDO> {
    default CollectorPointDO getCollectorPoint(Long pointId){
        return selectOne(CollectorPointDO::getPointId, pointId);
    }

   default PageResult<CollectorPointDO> getCollectorPointPage(CollectorPointPageReqVO reqVO){
        return selectPage(reqVO, new LambdaQueryWrapperX<CollectorPointDO>()
                .eqIfPresent(CollectorPointDO::getCollectorId, reqVO.getCollectorId())
                .eqIfPresent(CollectorPointDO::getDeviceId,reqVO.getDeviceId()));
   }

   default List<CollectorPointDO> getPointsByFrequency(Integer frequency,List<Long> collectorIds){
        return selectList(new LambdaQueryWrapperX<CollectorPointDO>().eq(CollectorPointDO::getFrequency, frequency)
                .in(CollectorPointDO::getCollectorId,collectorIds));
   }

    default List<CollectorPointDO> getPointByCollectorId(Long collectorId){
        return selectList(new LambdaQueryWrapperX<CollectorPointDO>()
                .eqIfPresent(CollectorPointDO::getCollectorId,collectorId));
    }

    default List<CollectorPointDO> getPointsByCollectorIds(List<Long> collectorIds){
        return selectList(new LambdaQueryWrapperX<CollectorPointDO>().in(CollectorPointDO::getCollectorId,collectorIds));
    }
}
