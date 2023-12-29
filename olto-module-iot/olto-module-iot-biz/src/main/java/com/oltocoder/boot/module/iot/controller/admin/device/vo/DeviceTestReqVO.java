package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeviceTestReqVO {

    @Schema(description = "主键")
    private Long id;
    @Schema(description = "设备指令")
    private String cmd;
    @Schema(description = "值")
    private String writeValue;
    @Schema(description = "值类型")
    private Integer type;
    @Schema(description = "起始地址")
    private Integer start;
}
