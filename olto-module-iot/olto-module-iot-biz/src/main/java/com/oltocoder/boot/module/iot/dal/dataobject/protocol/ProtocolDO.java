package com.oltocoder.boot.module.iot.dal.dataobject.protocol;

import com.oltocoder.boot.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("iot_protocol")
@EqualsAndHashCode(callSuper = true)
public class ProtocolDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 设协议id
     */
    @TableId
    private Long id;

    /**
     * 协议名
     */
    private String name;

    /**
     * 协议类型
     */
    private Integer protocolType;

    /**
     * 组件名称
     */
    private String componentName;
}
