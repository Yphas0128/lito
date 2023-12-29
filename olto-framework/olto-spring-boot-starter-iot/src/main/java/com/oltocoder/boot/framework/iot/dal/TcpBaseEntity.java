package com.oltocoder.boot.framework.iot.dal;

import lombok.Data;

/**
 * @title: TcpDo
 * @Author cmw
 * @Date: 2023/8/10 14:06
 * @describe
 */
@Data
public class TcpBaseEntity extends BaseEntity {
    /**
     * 指令
     */
    private String ip;
    private Integer port;

}
