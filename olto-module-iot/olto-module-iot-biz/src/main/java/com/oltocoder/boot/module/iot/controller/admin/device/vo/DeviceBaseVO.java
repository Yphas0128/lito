package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class DeviceBaseVO {

    @Schema(description = "设备编号",required = true)
    @NotEmpty(message = "设备编号不能为空")
    private String code;

    @Schema(description = "名称",required = true)
    @NotEmpty(message = "名称不能为空")
    @Size(min = 1,max = 50,message = "设备名字符个数限制在1至50个")
    private String name;


//    @Schema(description = "连接方式")
//    private String connectMode;

    @Schema(description = "设备ip")
    private String host;

    @Schema(description = "设备端口")
    private Integer port;

    @Schema(description = "串口")
    private String serialCom;

    @Schema(description = "串口-波特率")
    private Integer baudRate;

    @Schema(description = "串口-数据位")
    private Integer dataBits;

    @Schema(description = "串口-校验位")
    private Integer parityBits;

    @Schema(description = "串口-停止位")
    private Integer stopBits;
}
