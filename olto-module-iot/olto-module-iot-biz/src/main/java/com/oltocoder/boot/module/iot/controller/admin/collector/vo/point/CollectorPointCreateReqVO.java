package com.oltocoder.boot.module.iot.controller.admin.collector.vo.point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CollectorPointCreateReqVO {

    @Schema(description = "采集器")
    @NotNull
    private Long collectorId;

    @Schema(description = "设备")
    @NotNull
    private Long deviceId;

    @Schema(description = "点位")
    @NotNull
    private Long pointId;

    @Schema(description = "采集频率（秒）", required = true)
    @NotNull(message = "采集频率（秒）不能为空")
    private Integer frequency;


}
