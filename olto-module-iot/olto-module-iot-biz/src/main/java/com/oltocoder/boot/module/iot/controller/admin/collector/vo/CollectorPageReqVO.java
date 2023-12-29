package com.oltocoder.boot.module.iot.controller.admin.collector.vo;

import com.oltocoder.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Schema(description = "管理后台 - 采集器分页列表 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectorPageReqVO extends PageParam {

    @Schema(description = "采集器名", example = "机械臂采集器")
    @Size(max = 50, message = "采集器长度不能超过50个字符")
    private String name;
}
