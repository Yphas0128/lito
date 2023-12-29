package com.oltocoder.boot.module.iot.dal.dataobject.point;

import com.oltocoder.boot.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 采集器-点位
 */
@Data
@TableName("iot_point")
@EqualsAndHashCode(callSuper = true)
public class PointDO extends BaseDO {


    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 设备
     */
    private Long deviceId;

    /**
     * 信号类型
     */
    private Integer modbusType;

    /**
     * 字段类型
     */
    private Integer fieldType;

    /**
     * modbus地址
     */
    private String address;

    /**
     * 读取的寄存器(点位)数量
     */
    private Integer num;

    /**
     * 自定义报文
     */
    private String message;

    /**
     * 点位字段属性
     */
    private String property;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 状态
     */
    private Integer status;
}
