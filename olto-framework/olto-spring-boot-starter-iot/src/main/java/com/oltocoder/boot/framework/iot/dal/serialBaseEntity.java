package com.oltocoder.boot.framework.iot.dal;

import com.oltocoder.boot.framework.iot.core.consts.SerialStatus;
import lombok.Data;

import java.util.Date;

/**
 * @title: RtuBaseEntity
 * @Author cmw
 * @Date: 2023/8/10 16:17
 * @describe
 */
@Data
public class serialBaseEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    /**
     * 指令
     */
    private String cmd;

    /**
     * 串口
     */
    private String com;

    /**
     * 波特率
     */
    private Integer baudRate;

    /**
     * 数据位
     */
    private Integer dataBits;

    /**
     * 校验位
     */
    private Integer parity;

    /**
     * 停止位
     */
    private Integer stopBits;

    /**
     * 状态
     */
    private SerialStatus status;

    private Date createTime;
}
