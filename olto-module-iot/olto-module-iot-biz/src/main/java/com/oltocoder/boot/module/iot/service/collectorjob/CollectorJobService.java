package com.oltocoder.boot.module.iot.service.collectorjob;

import com.oltocoder.boot.framework.common.enums.FrequencyEnum;
import com.oltocoder.boot.module.iot.dal.dataobject.collectorjob.CollectorJobDO;
import org.quartz.SchedulerException;

import java.util.List;

public interface CollectorJobService  {

    void createJobs(Long id,List<Integer> frequencies)  throws SchedulerException;

    void createJob(FrequencyEnum frequencyEnum,String jobParams) throws SchedulerException;


    void saveJob(Long id,Integer frequency)  throws SchedulerException;

    void stopJobs(Long collectorId) throws SchedulerException;

    int updateJobStates(List<Integer> stopJobs,Integer state);

    int updateJobState(Integer frequency, Integer status);

    CollectorJobDO getCollectorJob(Integer frequency);

    void updateJobs() throws SchedulerException;
}
