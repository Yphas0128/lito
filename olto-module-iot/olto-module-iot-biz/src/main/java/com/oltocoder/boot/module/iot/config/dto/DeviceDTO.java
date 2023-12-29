package com.oltocoder.boot.module.iot.config.dto;

import lombok.Data;

@Data
public class DeviceDTO {
    /**
     * 设备编号
     */
    private String code;

    /**
     * 消息协议
     */
    private String componentName;


    /**
     * 设备ip
     */
    private String host;

    /**
     * 设备端口
     */
    private Integer port;

    /**
     * 串口
     */
    private String serialCom;

    /**
     * 串口-波特率
     */
    private Integer baudRate;

    /**
     * 串口-数据位
     */
    private Integer dataBits;

    /**
     * 串口-校验位
     */
    private Integer parityBits;

    /**
     * 串口-停止位
     */
    private Integer stopBits;

}
