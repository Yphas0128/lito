package com.oltocoder.boot.module.iot.controller.admin.collector;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.framework.common.pojo.CommonResult;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.*;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointRespVO;
import com.oltocoder.boot.module.iot.convert.collector.CollectorConvert;
import com.oltocoder.boot.module.iot.convert.collector.CollectorPointConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorDO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorPointDO;
import com.oltocoder.boot.module.iot.dal.dataobject.device.DeviceDO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.oltocoder.boot.module.iot.enums.CollectorRunningStateEnum;
import com.oltocoder.boot.module.iot.service.collector.CollectorService;
import com.oltocoder.boot.module.iot.service.collectorjob.CollectorJobService;
import com.oltocoder.boot.module.iot.service.device.DeviceService;
import com.oltocoder.boot.module.iot.service.point.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.oltocoder.boot.framework.common.pojo.CommonResult.error;
import static com.oltocoder.boot.framework.common.pojo.CommonResult.success;
import static com.oltocoder.boot.framework.common.util.collection.CollectionUtils.convertList;

@Tag(name = "iot数据采集 - 采集器")
@RequestMapping("/iot/collector")
@RestController
@PermitAll
public class CollectorController {

    @Resource(name = "collectorService")
    private CollectorService collectorService;

    @Resource(name = "pointService")
    private PointService pointService;

    @Resource(name = "deviceService")
    private DeviceService deviceService;

    @Resource(name = "collectorJobService")
    private CollectorJobService collectorJobService;

    @PostMapping("/create")
    @Operation(summary = "创建采集器")
    public CommonResult<Long> createCollector(@Valid @RequestBody CollectorCreateReqVO reqVO) {
        Long id = collectorService.createCollector(reqVO);
        return success(id);
    }

    @PutMapping("/update")
    @Operation(summary = "修改采集器")
    public CommonResult<Boolean> updateCollector(@Valid @RequestBody CollectorUpdateReqVO reqVO) {
        collectorService.updateCollector(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除采集器")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> deleteCollector(@RequestParam("id") Long id){
        collectorService.deleteCollector(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取采集器")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<CollectorRespVO> getCollector(@RequestParam("id") Long id){
        CollectorDO collectorDO = collectorService.getCollector(id);
        CollectorRespVO respVO = CollectorConvert.INSTANCE.convert(collectorDO);
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取采集器列表")
    public CommonResult<PageResult<CollectorRespVO>> getCollectorPage(@Valid CollectorPageReqVO reqVO){
        PageResult<CollectorDO> pageResult = collectorService.getCollectorPage(reqVO);
        return success((CollectorConvert.INSTANCE.convertPage(pageResult)));
    }

    @GetMapping("/get-simple-list")
    @Operation(summary = "获取采集器列表")
    public CommonResult<List<CollectorRespVO>> getCollectorList(){
        List<CollectorDO> collectors = collectorService.getCollectorList();
        List<CollectorRespVO> respVOS = CollectorConvert.INSTANCE.convertList(collectors);
        return success(respVOS);
    }

    @PostMapping("/create-point")
    @Operation(summary = "创建采集器关联点位")
    public CommonResult<Long> createCollectorPoint(@Valid @RequestBody CollectorPointCreateReqVO reqVO) {
        Long id = collectorService.createCollectorPoint(reqVO);
        return success(id);
    }

    @DeleteMapping("/delete-point")
    @Operation(summary = "删除采集器关联点位")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> deleteCollectorPoint(@RequestParam("id") Long id){
        collectorService.deleteCollectorPoint(id);
        return success(true);
    }

    @GetMapping("/page-point")
    @Operation(summary = "获取采集器关联点位")
    public CommonResult<PageResult<CollectorPointRespVO>> getCollectorPointPage(@Valid CollectorPointPageReqVO reqVO){
        PageResult<CollectorPointDO> pageResult = collectorService.getCollectorPointPage(reqVO);
        if(CollUtil.isEmpty(pageResult.getList()))
            return success(new PageResult<>(pageResult.getTotal()));
        List<Long> pointIds = convertList(pageResult.getList(), CollectorPointDO::getPointId);
        Map<Long, PointDO> pointMap = pointService.getPointMap(pointIds);
        List<CollectorPointRespVO> respVOS  = new ArrayList<>(pageResult.getList().size());

        List<Long> deviceIds = convertList(pageResult.getList(), CollectorPointDO::getDeviceId);
        Map<Long, DeviceDO> deviceMap = deviceService.getDeviceMap(deviceIds);

        pageResult.getList().forEach(collectorPointDO -> {
            CollectorPointRespVO respVO = CollectorPointConvert.INSTANCE.convert01(collectorPointDO);
            Optional.ofNullable(pointMap.get(collectorPointDO.getPointId())).ifPresent(pointDO -> {
                respVO.setName(pointDO.getName());
                respVO.setAddress(pointDO.getAddress());
                respVO.setMessage(pointDO.getMessage());
                respVO.setFieldType(pointDO.getFieldType());
                respVO.setModbusType(pointDO.getModbusType());
                respVO.setProperty(pointDO.getProperty());
                respVO.setNum(pointDO.getNum());
            });
            Optional.ofNullable(deviceMap.get(collectorPointDO.getDeviceId())).ifPresent(deviceDO -> respVO.setDeviceName(deviceDO.getName()));
            respVOS.add(respVO);
        });
        return success(new PageResult<>(respVOS, pageResult.getTotal()));
    }


    @PostMapping("/consume/{id}")
    @Operation(summary = "运行采集器")
    public CommonResult<Boolean> consumeCollector(@PathVariable("id") Long id){
        CollectorDO collector = collectorService.getCollector(id);
        List<CollectorPointDO> points = collectorService.getPointByCollectorId(id);
        try {
            if (collector.getRunningState().equals(CollectorRunningStateEnum.RUNNING.ordinal())) {
                collectorJobService.stopJobs(id); // ,List<Integer> reStops
                collectorService.updateCollectorState(id, CollectorRunningStateEnum.STOP.ordinal());
                collectorJobService.updateJobs();
            } else {
                List<Integer> frequencies = convertList(points, CollectorPointDO::getFrequency);
                if (CollUtil.isEmpty(frequencies))
                    return error(200001, "请先添加点位!");
                for (Integer frequency : frequencies) {
                    collectorJobService.saveJob(id, frequency);
                }
                collectorService.updateCollectorState(id, CollectorRunningStateEnum.RUNNING.ordinal());
            }
        }catch (SchedulerException e) {
            System.out.println(e.getMessage());
        }
        return success(true);
    }

}
