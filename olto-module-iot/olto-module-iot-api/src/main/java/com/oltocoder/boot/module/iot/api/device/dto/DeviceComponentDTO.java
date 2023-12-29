package com.oltocoder.boot.module.iot.api.device.dto;

import lombok.Data;

@Data
public class DeviceComponentDTO {

    /**
     * 设备主键
     */
    private Long id;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备编号
     */
    private String code;

    /**
     *
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
