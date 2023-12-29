package com.oltocoder.boot.module.iot.controller.admin.collector.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description="管理后台 - 修改采集器 Request VO")
@Data
public class CollectorUpdateReqVO extends CollectorBaseVO {

    @Schema(description = "id",required = true,example = "1111")
    @NotNull(message = "采集器id不能为空")
    private Long id;
}
