package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneActionProvider;
import com.oltocoder.boot.framework.common.enums.ActionTypeEnum;

import java.util.Collections;
import java.util.List;

/**
 * 报警
 */
public class AlarmActionProvider implements SceneActionProvider<AlarmAction> {

    @Override
    public String getProvider() {
         return ActionTypeEnum.alarm.name();
    }

    @Override
    public void applyRuleNode(AlarmAction alarmAction, RuleNodeModel nodeModel) {
            nodeModel.setExecutor(getProvider());
            nodeModel.setConfiguration(alarmAction.convertToMap());
    }

    @Override
    public List<String> parseColumns(AlarmAction config) {
        return Collections.emptyList();
    }
}
