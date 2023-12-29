package com.oltocoder.boot.module.iot.service.protocol;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.common.util.collection.CollectionUtils;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ProtocolService {
    Long createProtocol(ProtocolCreateReqVO reqVO);

    int updateProtocol(ProtocolUpdateReqVO reqVO);

    int deleteProtocol(Long id);

    ProtocolDO getProtocol(Long id);

    ProtocolDO getProtocolByName(String name);

    ProtocolDO getProtocolByNameNotId(String name, Long id);

    PageResult<ProtocolDO> getProtocolPage(ProtocolPageReqVO reqVO);

    List<ProtocolDO> getProtocolList();

    List<ProtocolDO> getProtocols(List<Long> ids);

    default Map<Long,ProtocolDO> getProtocolMap(List<Long> ids){
        if(CollUtil.isEmpty(ids))
            return new HashMap<>();
        List<ProtocolDO> protocols = getProtocols(ids);
        return CollectionUtils.convertMap(protocols,ProtocolDO::getId);
    }
}
