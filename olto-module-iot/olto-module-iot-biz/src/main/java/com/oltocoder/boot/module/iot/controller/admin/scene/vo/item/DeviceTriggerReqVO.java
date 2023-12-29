package com.oltocoder.boot.module.iot.controller.admin.scene.vo.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;


// 设备触发
@Data
public class DeviceTriggerReqVO {

    @NotNull
    @Schema(description = "产品ID")
    private Long productId;

    @NotNull
    @Schema(description = "触发类型 => DeviceOperationEnum")
    private Integer deviceOperator;

    @Schema(description = "[operator]为[readProperty,writeProperty,invokeFunction]时不能为空")
    private String cron;

    @Schema(description = "[operator]为[reportEvent]时不能为空")
    private String eventId;

    @Schema(description = "[operator]为[readProperty]时不能为空")
    private List<String> readProperties;

    @Schema(description = "[operator]为[writeProperty]时不能为空")
    private Map<String, Object> writeProperties;

    @Schema(description = "[operator]为[invokeFunction]时不能为空")
    private String functionId;

    @Schema(description = "[operator]为[invokeFunction]时不能为空")
    private List<Map<String,Object>> functionParameters;

}
