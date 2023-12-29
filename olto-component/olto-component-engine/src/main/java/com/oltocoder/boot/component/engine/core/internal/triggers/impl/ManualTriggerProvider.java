package com.oltocoder.boot.component.engine.core.internal.triggers.impl;

import com.oltocoder.boot.component.engine.core.internal.triggers.SceneTriggerProvider;
import com.oltocoder.boot.framework.common.enums.TriggerTypeEnum;

public class ManualTriggerProvider implements SceneTriggerProvider<ManualTrigger> {

    @Override
    public String getProvider() {
        return TriggerTypeEnum.manual.name();
    }

    @Override
    public ManualTrigger newConfig() {
        return new ManualTrigger();
    }
}
