package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import com.oltocoder.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
public class DevicePageReqVO extends PageParam {

    @Schema(description = "设备名")
    @Size(max = 50,message = "设备名的长度不能超过50个字符")
    private String code;

}
