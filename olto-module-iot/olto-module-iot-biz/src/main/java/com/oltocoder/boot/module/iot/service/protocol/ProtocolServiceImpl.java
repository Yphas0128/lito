package com.oltocoder.boot.module.iot.service.protocol;

import cn.hutool.core.util.ObjUtil;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolUpdateReqVO;
import com.oltocoder.boot.module.iot.convert.protocol.ProtocolConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;
import com.oltocoder.boot.module.iot.dal.mysql.protocol.ProtocolMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.oltocoder.boot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.oltocoder.boot.module.system.enums.ErrorCodeConstants.PROTOCOL_IS_EXISTS;

@Service("protocolService")
public class ProtocolServiceImpl implements ProtocolService {

    @Resource
    private ProtocolMapper protocolMapper;

    @Override
    public Long createProtocol(ProtocolCreateReqVO reqVO) {
        ProtocolDO protocolDO = getProtocolByName(reqVO.getName());
        if(ObjUtil.isNotNull(protocolDO))
            throw exception(PROTOCOL_IS_EXISTS);
         protocolDO = ProtocolConvert.INSTANCE.convert01(reqVO);
        int count = protocolMapper.insert(protocolDO);
        return count >0 ? protocolDO.getId(): 0L;
    }

    @Override
    public int updateProtocol(ProtocolUpdateReqVO reqVO) {
        ProtocolDO protocolDO = getProtocolByNameNotId(reqVO.getName(),reqVO.getId());
        if(ObjUtil.isNotNull(protocolDO))
            throw exception(PROTOCOL_IS_EXISTS);
        protocolDO = ProtocolConvert.INSTANCE.convert02(reqVO);
        return protocolMapper.updateById(protocolDO);
    }

    @Override
    public int deleteProtocol(Long id) {
        return protocolMapper.deleteById(id);
    }

    @Override
    public ProtocolDO getProtocol(Long id) {
        return protocolMapper.selectById(id);
    }

    @Override
    public ProtocolDO getProtocolByName(String name) {
        return protocolMapper.getProtocolByName(name);
    }

    @Override
    public ProtocolDO getProtocolByNameNotId(String name, Long id) {
        return protocolMapper.getProtocolByNameNotId(name,id);
    }

    @Override
    public PageResult<ProtocolDO> getProtocolPage(ProtocolPageReqVO reqVO) {
        return protocolMapper.pageResult(reqVO);
    }

    @Override
    public List<ProtocolDO> getProtocolList() {
        return protocolMapper.selectList();
    }

    @Override
    public List<ProtocolDO> getProtocols(List<Long> ids) {
        return protocolMapper.selectBatchIds(ids);
    }
}
