package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/*
 * 通知
 */
@Getter
@Setter
public class NotifyAction {
    /**
     *通知类型
     */
    private String notifyType;

    /**
     * 通知配置ID
     */
    private String notifierId;

    /**
     *通知模版ID
     */
    private String templateId;

    /**
     * 通知变量
     */
    private Map<String, Object> variables;

    public Map<String,Object> convertToMap(){
        return BeanUtil.beanToMap(this);
    }

    // todo 未完待续
    public List<String> parseColumns() {

        return null;
    }
}
