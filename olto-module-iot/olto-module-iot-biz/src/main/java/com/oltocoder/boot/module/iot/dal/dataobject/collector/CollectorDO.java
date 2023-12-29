package com.oltocoder.boot.module.iot.dal.dataobject.collector;

import com.oltocoder.boot.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("iot_collector")
@EqualsAndHashCode(callSuper = true)
public class CollectorDO extends BaseDO {

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
//
//    /**
//     * 协议
//     */
//    private Long deviceId;


    /**
     * 运行状态
     */
    private Integer runningState;

    /**
     * 状态
     */
    private Integer state;
}
