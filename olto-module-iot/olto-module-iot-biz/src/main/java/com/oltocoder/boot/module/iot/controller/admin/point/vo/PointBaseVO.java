package com.oltocoder.boot.module.iot.controller.admin.point.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PointBaseVO {

//    @Schema(description = "采集器id", required = true, example = "111")
//    @NotNull(message = "采集器id不能为空")
//    private Long collectorId;

    @Schema(description = "采集器id", required = true, example = "111")
    @NotNull(message = "采集器id不能为空")
    private Long deviceId;

    @Schema(description = "名称", required = true, example = "111")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "点位字段属性")
    @NotEmpty(message = "字段属性不能为空")
    private String property;

//    @Schema(description = "采集频率（秒）", required = true, example = "111")
//    @NotNull(message = "采集频率（秒）不能为空")
//    private Integer frequency;

//    @Schema(description = "信号类型(1. 点位 2. 自定义报文)", required = true, example = "111")
//    @NotNull(message = "信号类型不能为空")
//    private Integer signType;

    @Schema(description = "modbus类型")
    private Integer modbusType;

    @Schema(description = "字段类型")
    private Integer fieldType;

    @Schema(description = "点位地址")
    private String address;

    @Schema(description = "读取的寄存器(点位)数量")
    private Integer num;

    @Schema(description = "自定义报文")
    private String message;

}
