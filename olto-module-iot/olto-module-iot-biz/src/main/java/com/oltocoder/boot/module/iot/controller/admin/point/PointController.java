package com.oltocoder.boot.module.iot.controller.admin.point;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.framework.common.enums.ModbusTypeEnum;
import com.oltocoder.boot.framework.common.enums.SignFieldEnum;
import com.oltocoder.boot.framework.common.pojo.CommonResult;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.*;
import com.oltocoder.boot.module.iot.convert.point.PointConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.oltocoder.boot.module.iot.service.point.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.oltocoder.boot.framework.common.pojo.CommonResult.success;

@Tag(name = "iot数据采集 - 采集器点位")
@RestController
@RequestMapping("/iot/point")
@PermitAll
public class PointController {

    @Resource(name = "pointService")
    private PointService pointService;

    @PostMapping("/create")
    @Operation(summary = "创建点位")
    public CommonResult<Long> createPoint(@Valid @RequestBody PointCreateReqVO reqVO) {
        Long id = pointService.createPoint(reqVO);
        return success(id);
    }

    @PutMapping("/update")
    @Operation(summary = "修改点位")
    public CommonResult<Boolean> updatePoint(@Valid @RequestBody PointUpdateReqVO reqVO) {
        pointService.updatePoint(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除点位")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> deletePoint(@RequestParam("id") Long id){
        pointService.deletePoint(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获取点位详情")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<PointRespVO> getPoint(@RequestParam("id") Long id){
        PointDO Point = pointService.getPoint(id);
        PointRespVO respVO = PointConvert.INSTANCE.convert(Point);
        Optional.ofNullable(Point.getModbusType()).ifPresent(modbusType -> respVO.setModbusTypeName(ModbusTypeEnum.valueOf(modbusType).getName()));
        Optional.ofNullable(Point.getFieldType()).ifPresent(filedType -> respVO.setFieldTypeName(SignFieldEnum.valueOf(filedType).getName()));
        return success(respVO);
    }

    @GetMapping("/page")
    @Operation(summary = "分页获取点位列表")
    public CommonResult<PageResult<PointRespVO>> getPointPage(@Valid PointPageReqVO reqVO){
        PageResult<PointDO> pageResult = pointService.getPointPage(reqVO);
        if(CollUtil.isEmpty(pageResult.getList()))
            return success(new PageResult<>(pageResult.getTotal()));

        List<PointRespVO> respVOS = new ArrayList<>();

        pageResult.getList().forEach(Point -> {
            PointRespVO respVO = PointConvert.INSTANCE.convert(Point);
            Optional.ofNullable(Point.getModbusType()).ifPresent(modbusType -> respVO.setModbusTypeName(ModbusTypeEnum.valueOf(modbusType).getName()));
            Optional.ofNullable(Point.getFieldType()).ifPresent(filedType -> respVO.setFieldTypeName(SignFieldEnum.valueOf(filedType).getName()));
            respVOS.add(respVO);
        });
        return success((new PageResult<>(respVOS, pageResult.getTotal())));
    }

    @PutMapping("/enable")
    @Operation(summary = "启用")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> enablePoint(@RequestParam("id") Long id){
        pointService.updatePointEnable(id, true);
        return success(true);
    }

    @PutMapping("/disable")
    @Operation(summary = "禁用")
    @Parameter(name = "id",description = "id",required = true)
    public CommonResult<Boolean> disablePoint(@RequestParam("id") Long id){
        pointService.updatePointEnable(id, false);
        return success(true);
    }



}
