package com.oltocoder.boot.module.iot.dal.dataobject.collector;

import com.oltocoder.boot.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@TableName("iot_collector_point")
@EqualsAndHashCode(callSuper = true)
public class CollectorPointDO extends BaseDO {


    private static final long serialVersionUID = 1L;

    /**
     * 设协议id
     */
    @TableId
    private Long id;

    /**
     *
     */
    private Long collectorId;

    /**
     *
     */
    private Long deviceId;

    /**
     *
     */
    private Long pointId;

    /**
     * 采集频率（秒）
     */
    private Integer frequency;
}
