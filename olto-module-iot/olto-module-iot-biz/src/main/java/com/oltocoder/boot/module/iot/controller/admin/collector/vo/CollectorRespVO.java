package com.oltocoder.boot.module.iot.controller.admin.collector.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 采集器详情 Response VO")
@Data
public class CollectorRespVO extends CollectorBaseVO {

    @Schema(description = "id",required = true,example = "1111")
    private Long id;

    @Schema(description = "创建时间",required = true,example = "时间戳格式")
    private LocalDateTime createTime;

    @Schema(description = "采集器运行状态  1=> 已停止 2=> 运行中")
    private Integer runningState;

    @Schema(description = "采集器状态 1=> 正常 2=> 禁用")
    private Integer state;
}
