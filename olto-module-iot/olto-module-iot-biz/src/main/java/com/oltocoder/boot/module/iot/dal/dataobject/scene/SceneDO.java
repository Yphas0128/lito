package com.oltocoder.boot.module.iot.dal.dataobject.scene;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.oltocoder.boot.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("iot_scene")
@EqualsAndHashCode(callSuper = true)
public class SceneDO extends BaseDO {

    @TableId
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String triggerType;

    /**
     *
     */
    private String sceneTrigger;

    private boolean parallel;

    /**
     * 条件
     */
    private String terms;

    /**
     * 执行动作
     */
    private String actions;

    /**
     * 动作分支
     */
    private String branches;

    /**
     * 扩展属性
     */
    private String configuration;
}
