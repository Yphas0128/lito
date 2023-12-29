package com.oltocoder.boot.framework.iot.dal;

import lombok.Data;

/**
 * @title: BaseEntity
 * @Author cmw
 * @Date: 2023/8/10 15:07
 * @describe
 */
@Data
public class BaseEntity {
    /**
     * 设备编号
     */
    private String deviceSn;
    /**
     * 需要写入的数据
     */
    private String writeValue;
}
