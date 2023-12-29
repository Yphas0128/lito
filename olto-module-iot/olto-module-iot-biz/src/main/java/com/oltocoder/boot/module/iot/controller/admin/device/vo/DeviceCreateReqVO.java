package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class DeviceCreateReqVO extends DeviceBaseVO {

    @Schema(description = "消息协议",required = true)
    private Long protocol;

}
