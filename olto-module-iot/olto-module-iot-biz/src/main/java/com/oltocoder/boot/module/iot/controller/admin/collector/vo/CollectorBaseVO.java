package com.oltocoder.boot.module.iot.controller.admin.collector.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class CollectorBaseVO {

    @Schema(description = "名称",required = true,example = "机械臂采集器")
    @NotEmpty(message = "名称不能为空")
    @Length(min = 1,max = 50,message = "名称长度为 1-50 位")
    private String name;

//    @Schema(description = "采集动作",required = true,example = "collect_action_modbus")
//    private String collectAction;

//    @Schema(description = "设备id",required = true,example = "11111")
//    @NotNull(message = "设备id不能为空")
//    private Long deviceId;

}
