package com.oltocoder.boot.module.iot.dal.mysql.collectorjob;

import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.iot.dal.dataobject.collectorjob.CollectorJobDO;
import com.oltocoder.boot.module.iot.enums.CollectorJobStatusEnum;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CollectorJobMapper extends BaseMapperX<CollectorJobDO> {
   default CollectorJobDO getCollectorJob(Integer frequency){
       return selectOne(CollectorJobDO::getFrequency, frequency);
   }

    default int updateJobStates(List<Integer> frequencies, Integer status){
        return update(null, new UpdateWrapper<CollectorJobDO>().in("frequency",frequencies).set("status", status));
    }

    default int updateJobState(Integer frequency, Integer status){
       return update(new CollectorJobDO().setStatus(status), new LambdaQueryWrapperX<CollectorJobDO>().eq(CollectorJobDO::getFrequency, frequency));
    }
}
