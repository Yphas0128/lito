package com.oltocoder.boot.framework.iot.dal;

import lombok.Data;

/**
 * @title: ModBusTtuBaseEntity
 * @Author cmw
 * @Date: 2023/8/10 16:24
 * @describe
 */
@Data
public class ModBusRtuBaseEntity extends serialBaseEntity{

    /**
     * 数据类型
     */
    private Integer fieldType;

    /**
     * 开始读取位
     */
    private Integer startAddress;

    /**
     * 从站地址
     */
    private Integer slaveAddress;
    /**
     * 读写位数
     */
    private Integer num;
}
