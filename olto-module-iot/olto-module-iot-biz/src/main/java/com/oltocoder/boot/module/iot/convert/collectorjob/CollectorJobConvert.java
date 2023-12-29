package com.oltocoder.boot.module.iot.convert.collectorjob;

import com.oltocoder.boot.module.iot.api.device.dto.DeviceComponentDTO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.oltocoder.boot.module.iot.service.collectorjob.dto.JobDeviceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface CollectorJobConvert {

    CollectorJobConvert INSTANCE = Mappers.getMapper(CollectorJobConvert.class);

   default List<JobDeviceDTO> convertJobs(Map<Long, List<PointDO>> devicePointMap, List<DeviceComponentDTO> devices){
       List<JobDeviceDTO> jobs = new ArrayList<>(devices.size());
       devices.forEach(deviceComponentDTO -> {
           JobDeviceDTO jobDeviceDTO = convertDevice(deviceComponentDTO);
           boolean flag = Optional.ofNullable(devicePointMap.get(deviceComponentDTO.getId())).isPresent();
           if(flag){
               jobDeviceDTO.setPoints(devicePointMap.get(deviceComponentDTO.getId()));
           }
           jobs.add(jobDeviceDTO);
       });
       return jobs;
   }

    JobDeviceDTO convertDevice(DeviceComponentDTO deviceComponentDTO);
}
