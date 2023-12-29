package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeviceRespVO extends DeviceBaseVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "消息协议")
    private Long protocol;

    @Schema(description = "消息协议")
    private Integer protocolType;

    @Schema(description = "状态")
    private Integer state;
}
