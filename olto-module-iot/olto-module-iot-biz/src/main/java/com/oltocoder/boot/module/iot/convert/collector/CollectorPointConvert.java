package com.oltocoder.boot.module.iot.convert.collector;

import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointRespVO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorPointDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectorPointConvert {

    CollectorPointConvert INSTANCE = Mappers.getMapper(CollectorPointConvert.class);

    CollectorPointDO convert(CollectorPointCreateReqVO reqVO);

    CollectorPointRespVO convert01(CollectorPointDO collectorPointDO);
}
