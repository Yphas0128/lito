package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceUpdateReqVO extends DeviceBaseVO {

    @Schema(description = "主键Id")
    private Long id;

    @Schema(description = "消息协议",required = true)
    private Long protocol;
}
