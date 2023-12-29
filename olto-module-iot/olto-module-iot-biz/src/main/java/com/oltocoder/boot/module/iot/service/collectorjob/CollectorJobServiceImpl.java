package com.oltocoder.boot.module.iot.service.collectorjob;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.oltocoder.boot.framework.common.enums.FrequencyEnum;
import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.config.CollectorConfiguration;
import com.oltocoder.boot.framework.quartz.core.scheduler.SchedulerManager;
import com.oltocoder.boot.module.iot.api.device.dto.DeviceComponentDTO;
import com.oltocoder.boot.module.iot.convert.collectorjob.CollectorJobConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorDO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorPointDO;
import com.oltocoder.boot.module.iot.dal.dataobject.collectorjob.CollectorJobDO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.oltocoder.boot.module.iot.dal.mysql.collectorjob.CollectorJobMapper;
import com.oltocoder.boot.module.iot.enums.CollectorJobStatusEnum;
import com.oltocoder.boot.module.iot.enums.CollectorRunningStateEnum;
import com.oltocoder.boot.module.iot.service.collector.CollectorService;
import com.oltocoder.boot.module.iot.service.collectorjob.dto.JobDeviceDTO;
import com.oltocoder.boot.module.iot.service.device.DeviceService;
import com.oltocoder.boot.module.iot.service.point.PointService;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.oltocoder.boot.framework.common.util.collection.CollectionUtils.convertList;

@Service("collectorJobService")
public class CollectorJobServiceImpl implements CollectorJobService {

    @Resource
    private SchedulerManager schedulerManager;
    @Resource
    private CollectorConfiguration collectorConfiguration;
    @Resource
    private CollectorJobMapper collectorJobMapper;
    @Resource(name = "pointService")
    private PointService pointService;
    @Resource(name = "deviceService")
    private DeviceService deviceService;
    @Resource(name = "collectorService")
    private CollectorService collectorService;


    @Override
    public void createJobs(Long id,List<Integer> frequencies) throws SchedulerException {
        for (Integer frequency: frequencies) {
            boolean flag = Optional.ofNullable(FrequencyEnum.valueOf(frequency)).isPresent();
            if (flag) {
                FrequencyEnum frequencyEnum = FrequencyEnum.valueOf(frequency);// jobName
                CollectorJobDO collectorJob = getCollectorJob(frequency);
                List<JobDeviceDTO> jobParams = getJobParams(id,frequencyEnum.getValue());
                if(ObjUtil.isNull(collectorJob)){
                    createJob(frequencyEnum, JsonUtils.toJsonString(jobParams));
                }else{

                    schedulerManager.updateJob(frequencyEnum.getName(),
                            JsonUtils.toJsonString(jobParams),
                            frequencyEnum.getCron(),
                            collectorConfiguration.getCount(),
                            collectorConfiguration.getInterval()); // 重新启动任务
                }
            }
        }
    }



    @Override
//    @Transactional(rollbackFor = Exception.class)
    public void createJob(FrequencyEnum frequencyEnum,String jobParams) throws SchedulerException {
        CollectorJobDO collectorJobDO = new CollectorJobDO().setName(frequencyEnum.getName())
                .setFrequency(frequencyEnum.getValue())
                .setRetryCount(collectorConfiguration.getCount())
                .setRetryInterval(collectorConfiguration.getInterval());
        collectorJobMapper.insert(collectorJobDO);
        schedulerManager.addJob(collectorJobDO.getId(), frequencyEnum.getName(),"CollectorJobHandler",
                jobParams,
                frequencyEnum.getCron(),
                collectorConfiguration.getCount(),
                collectorConfiguration.getInterval());
        collectorJobDO.setStatus(CollectorJobStatusEnum.NORMAL.getStatus());
        collectorJobMapper.updateById(collectorJobDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(Long id,Integer frequency) throws SchedulerException {
        boolean flag = Optional.ofNullable(FrequencyEnum.valueOf(frequency)).isPresent();
        if(flag){
            FrequencyEnum frequencyEnum = FrequencyEnum.valueOf(frequency);// jobName
            CollectorJobDO collectorJob = getCollectorJob(frequency);
            List<JobDeviceDTO> jobParams = getJobParams(id,frequencyEnum.getValue());
            if(ObjUtil.isNull(collectorJob) && collectorJob.getStatus().equals(CollectorJobStatusEnum.NORMAL.getStatus())){
                CollectorJobDO collectorJobDO = new CollectorJobDO().setName(frequencyEnum.getName())
                        .setFrequency(frequencyEnum.getValue())
                        .setRetryCount(collectorConfiguration.getCount())
                        .setRetryInterval(collectorConfiguration.getInterval());
                collectorJobMapper.insert(collectorJobDO);
                schedulerManager.addJob(collectorJobDO.getId(), frequencyEnum.getName(),
                        "collectorJobHandler",
                        JsonUtils.toJsonString(jobParams),
                        frequencyEnum.getCron(),
                        collectorConfiguration.getCount(),
                        collectorConfiguration.getInterval());
                collectorJobDO.setStatus(CollectorJobStatusEnum.NORMAL.getStatus());
                collectorJobMapper.updateById(collectorJobDO);
            }else{
                schedulerManager.updateJob(frequencyEnum.getName(),
                        JsonUtils.toJsonString(jobParams),
                        frequencyEnum.getCron(),
                        collectorConfiguration.getCount(),
                        collectorConfiguration.getInterval()); // 重新启动任务
            }
        }
    }

    public List<JobDeviceDTO> getJobParams(Long id,Integer frequency){
        List<CollectorDO> collectors = collectorService.getCollectorByRunningState(CollectorRunningStateEnum.RUNNING.ordinal());
        List<Long> collectorIds = Optional.ofNullable(convertList(collectors, CollectorDO::getId)).orElse(new ArrayList<>(1));
        collectorIds.add(id);
        List<CollectorPointDO> collectorPoints = collectorService.getPointsByFrequency(frequency, collectorIds);
        List<Long> pointIds = convertList(collectorPoints, CollectorPointDO::getPointId);
        List<PointDO> points = pointService.getPoints(pointIds);
        List<Long> deviceIds = convertList(points, PointDO::getDeviceId);
        Map<Long, List<PointDO>> devicePointMap = points.stream().collect(Collectors.groupingBy(PointDO::getDeviceId));

        List<DeviceComponentDTO> devices  = deviceService.getDeviceComponents(deviceIds);
        return CollectorJobConvert.INSTANCE.convertJobs(devicePointMap, devices);
    }

    // 事务?
    @Override
    public void stopJobs(Long collectorId) throws SchedulerException {
        List<CollectorPointDO> collectorPoints = collectorService.getPointByCollectorId(collectorId);
        List<Integer> stopJobs = convertList(collectorPoints, CollectorPointDO::getFrequency);
        if(CollUtil.isNotEmpty(stopJobs)){
            for (Integer frequency: stopJobs){
                String jobName = FrequencyEnum.valueOf(frequency).getName(); // jobName
                schedulerManager.pauseJob(jobName);
                updateJobState(frequency, CollectorJobStatusEnum.STOP.getStatus());
            }
        }
    }

    @Override
    public int updateJobState(Integer frequency, Integer status) {
        return collectorJobMapper.updateJobState(frequency,status);
    }

    @Override
    public int updateJobStates(List<Integer> frequencies,Integer status) {
       return collectorJobMapper.updateJobStates(frequencies,status);
    }


    @Override
    public CollectorJobDO getCollectorJob(Integer frequency) {
        return collectorJobMapper.getCollectorJob(frequency);
    }

    @Override
    public void updateJobs() throws SchedulerException {
        List<CollectorDO> collectors = collectorService.getCollectorByRunningState(CollectorRunningStateEnum.RUNNING.ordinal());
        if(CollUtil.isEmpty(collectors))
            return;

        List<Long> collectorIds = convertList(collectors, CollectorDO::getId);
        List<CollectorPointDO> collectorPoints= collectorService.getPointsByCollectorIds(collectorIds);
        List<Integer> frequencies = convertList(collectorPoints, CollectorPointDO::getFrequency);
        for (Integer frequency: frequencies){
            FrequencyEnum frequencyEnum = FrequencyEnum.valueOf(frequency);// jobName

            List<Long> points = collectorPoints.stream().filter(collectorPointDO -> frequency.equals(collectorPointDO.getFrequency())).map(CollectorPointDO::getPointId)
                    .collect(Collectors.toList());
            List<JobDeviceDTO> jobParams = getUpdateJobParams(points);
            schedulerManager.updateJob(frequencyEnum.getName(),
                JsonUtils.toJsonString(jobParams),
                frequencyEnum.getCron(),
                collectorConfiguration.getCount(),
                collectorConfiguration.getInterval()); // 重新启动任务
        }
    }

    private List<JobDeviceDTO> getUpdateJobParams(List<Long> pointIds) {
        List<PointDO> points = pointService.getPoints(pointIds);
        List<Long> deviceIds = convertList(points, PointDO::getDeviceId);
        Map<Long, List<PointDO>> devicePointMap = points.stream().collect(Collectors.groupingBy(PointDO::getDeviceId));

        List<DeviceComponentDTO> devices  = deviceService.getDeviceComponents(deviceIds);
        return CollectorJobConvert.INSTANCE.convertJobs(devicePointMap, devices);
    }


}
