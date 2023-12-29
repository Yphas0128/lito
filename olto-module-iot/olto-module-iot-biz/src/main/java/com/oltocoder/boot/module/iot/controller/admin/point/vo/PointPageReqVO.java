package com.oltocoder.boot.module.iot.controller.admin.point.vo;


import com.oltocoder.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 采集器点位分页列表 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PointPageReqVO extends PageParam {

    @Schema(description = "采集器id")
    @NotNull(message = "采集器id不能为空")
    private Long deviceId;
}
