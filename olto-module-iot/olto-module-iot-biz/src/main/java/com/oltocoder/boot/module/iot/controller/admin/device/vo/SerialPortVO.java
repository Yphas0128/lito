package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SerialPortVO {

    @Schema(description = "串口")
    private String value;

    @Schema(description = "串口")
    private String name;
}
