package com.oltocoder.boot.module.iot.convert.collector;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorRespVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CollectorConvert {

    CollectorConvert INSTANCE = Mappers.getMapper(CollectorConvert.class);

    CollectorRespVO convert(CollectorDO collectorDO);

    PageResult<CollectorRespVO> convertPage(PageResult<CollectorDO> pageResult);

    List<CollectorRespVO> convertList(List<CollectorDO> collectors);

    CollectorDO convert01(CollectorCreateReqVO reqVO);

    CollectorDO convert02(CollectorUpdateReqVO reqVO);
}
