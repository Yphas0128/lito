package com.oltocoder.boot.module.iot.convert.point;

import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointRespVO;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointConvert {

    PointConvert INSTANCE = Mappers.getMapper(PointConvert.class);

    PointRespVO convert(PointDO point);

    PointDO convert01(PointCreateReqVO reqVO);

    PointDO convert02(PointUpdateReqVO reqVO);
}
