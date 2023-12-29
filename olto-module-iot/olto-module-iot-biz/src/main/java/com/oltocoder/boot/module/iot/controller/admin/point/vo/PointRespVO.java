package com.oltocoder.boot.module.iot.controller.admin.point.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 采集器点位详情 Response VO")
@Data
public class PointRespVO extends PointBaseVO {

    @Schema(description = "id",required = true,example = "111")
    private Long id;

    @Schema(description = "modbus")
    private String modbusTypeName;

    @Schema(description = "field")
    private String fieldTypeName;
}
