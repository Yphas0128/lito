package com.oltocoder.boot.module.iot.dal.dataobject.device;

import com.oltocoder.boot.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("iot_device")
@EqualsAndHashCode(callSuper = true)
public class DeviceDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    @TableId
    private Long id;

    /**
     * 设备编号
     */
    private String code;

    /**
     * 设备名
     */
    private String name;

    /**
     * 消息协议
     */
    private Long protocol;

    /**
     * 连接方式
     */
    private String connectMode;

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

    /**
     * 状态
     */
    private Integer state;
}
