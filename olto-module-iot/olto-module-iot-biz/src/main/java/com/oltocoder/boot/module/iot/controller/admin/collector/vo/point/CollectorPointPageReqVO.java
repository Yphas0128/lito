package com.oltocoder.boot.module.iot.controller.admin.collector.vo.point;

import com.oltocoder.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CollectorPointPageReqVO extends PageParam {

    @Schema(description = "采集器")
    private Long collectorId;

    @Schema(description = "设备")
    private Long deviceId;
}
