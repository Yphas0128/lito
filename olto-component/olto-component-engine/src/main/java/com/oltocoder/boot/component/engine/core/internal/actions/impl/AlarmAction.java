package com.oltocoder.boot.component.engine.core.internal.actions.impl;


import cn.hutool.core.bean.BeanUtil;

import java.util.Map;

//
public class AlarmAction {




    public Map<String, Object> convertToMap() {
        return BeanUtil.beanToMap(this);
    }
}
