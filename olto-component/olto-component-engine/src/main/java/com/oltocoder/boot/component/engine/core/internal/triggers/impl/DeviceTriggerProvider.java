package com.oltocoder.boot.component.engine.core.internal.triggers.impl;

import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.triggers.SceneTriggerProvider;
import com.oltocoder.boot.framework.common.enums.TriggerTypeEnum;

/**
 * @title: DeviceTriggerProvider
 * @Author Ypier
 * @Date: 2023/9/2 13:57
 */
public class DeviceTriggerProvider implements SceneTriggerProvider<DeviceTrigger> {

    @Override
    public String getProvider() {
        return TriggerTypeEnum.device.name();
    }

    @Override
    public void applyRuleNode(DeviceTrigger config, RuleModel model, RuleNodeModel sceneNode) {
        config.applyModel(model, sceneNode);
    }

    @Override
    public DeviceTrigger newConfig() {
        return new DeviceTrigger();
    }
}
