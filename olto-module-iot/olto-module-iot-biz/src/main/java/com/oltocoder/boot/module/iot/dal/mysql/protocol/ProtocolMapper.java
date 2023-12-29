package com.oltocoder.boot.module.iot.dal.mysql.protocol;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.iot.controller.admin.protocol.vo.ProtocolPageReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.protocol.ProtocolDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProtocolMapper extends BaseMapperX<ProtocolDO> {
   default PageResult<ProtocolDO> pageResult(ProtocolPageReqVO reqVO){
       return  selectPage(reqVO, new LambdaQueryWrapperX<ProtocolDO>().likeIfPresent(ProtocolDO::getName, reqVO.getName()));
   }

   default ProtocolDO getProtocolByName(String name){
       return selectOne(ProtocolDO::getName, name);
   }

    default ProtocolDO getProtocolByNameNotId(String name, Long id){
       return selectOne(new LambdaQueryWrapperX<ProtocolDO>().eq(ProtocolDO::getName, name).ne(ProtocolDO::getId, id));
    }
}
