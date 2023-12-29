package com.oltocoder.boot.module.iot.controller.admin.point.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 修改采集器点位 Request VO")
@Data
public class PointUpdateReqVO extends PointBaseVO {

    @Schema(description = "id",required = true,example = "111")
    @NotNull(message = "id不能为空")
    private Long id;
}