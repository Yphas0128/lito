package com.oltocoder.boot.module.iot.controller.admin.protocol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProtocolBaseVO {

    @Schema(description = "名称",required = true)
    @NotEmpty(message = "名称不能为空")
    @Size(min = 1,max = 50,message = "协议名字符个数限制在1至50个")
    private String name;


    @Schema(description = "协议类型",required = true)
    @NotNull(message = "名称不能为空")
    private Integer protocolType;

    @Schema(description = "组件名称",required = true)
    @NotEmpty(message = "组件名称不能为空")
    private String componentName;
}
