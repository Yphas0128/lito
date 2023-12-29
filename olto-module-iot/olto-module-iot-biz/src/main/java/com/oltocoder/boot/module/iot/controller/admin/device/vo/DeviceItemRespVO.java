package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceItemRespVO extends DeviceBaseVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "消息协议")
    private String messageProtocol;

    @Schema(description = "消息协议")
    private Integer protocolType;

    @Schema(description = "状态")
    private Integer state;
}
