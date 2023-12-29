package com.oltocoder.boot.module.iot.dal.mysql.collector;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorPageReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectorMapper extends BaseMapperX<CollectorDO> {
    /**
     * page
     */
   default PageResult<CollectorDO> pageResult(CollectorPageReqVO reqVO){
       return selectPage(reqVO, new LambdaQueryWrapperX<CollectorDO>().likeIfPresent(CollectorDO::getName, reqVO.getName()));
   }


    default int updateCollectorState(Long id, Integer state){
        return update(new CollectorDO().setState(state), new LambdaQueryWrapperX<CollectorDO>().eq(CollectorDO::getId, id));
    }

    default List<CollectorDO> getCollectorByRunningState(int state){
       return selectList(CollectorDO::getRunningState, state);
    }

}
