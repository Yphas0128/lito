package com.oltocoder.boot.module.iot.convert.protocol;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolRespVO;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProtocolConvert {
    ProtocolConvert INSTANCE = Mappers.getMapper(ProtocolConvert.class);

    List<ProtocolRespVO> convertList(List<ProtocolDO> protocols);

    PageResult<ProtocolRespVO> convertPage(PageResult<ProtocolDO> pageResult);

    ProtocolRespVO convert(ProtocolDO protocol);

    ProtocolDO convert01(ProtocolCreateReqVO reqVO);

    ProtocolDO convert02(ProtocolUpdateReqVO reqVO);

}
