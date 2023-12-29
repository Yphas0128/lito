package com.oltocoder.boot.module.iot.controller.admin.collector.vo.point;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CollectorPointRespVO {

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "采集器")
    private Long collectorId;

    @Schema(description = "设备")
    private Long deviceId;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "点位")
    private Long pointId;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "点位字段属性")
    private String property;

    @Schema(description = "采集频率（秒）", required = true, example = "111")
    private Integer frequency;

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
