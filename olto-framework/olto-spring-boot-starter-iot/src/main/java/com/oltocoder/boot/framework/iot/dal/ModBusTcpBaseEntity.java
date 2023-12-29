package com.oltocoder.boot.framework.iot.dal;

import lombok.Data;

/**
 * @title: ModBusTcpBaseEntity
 * @Author cmw
 * @Date: 2023/8/10 14:13
 * @describe
 */
@Data
public class ModBusTcpBaseEntity extends TcpBaseEntity{
    /**
     * modbus指令
     */
    private String cmd;
    /**
     * 数据类型
     */
    private Integer fieldType;
    /**
     * 开始读取位
     */
    private Integer startAddress;
    /**
     * 读写位数
     */
    private int num;
}
