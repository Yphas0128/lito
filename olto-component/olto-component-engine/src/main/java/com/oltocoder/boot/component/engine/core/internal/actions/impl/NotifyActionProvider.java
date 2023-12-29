package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneActionProvider;
import com.oltocoder.boot.framework.common.enums.ActionTypeEnum;

import java.util.List;

public class NotifyActionProvider implements SceneActionProvider<NotifyAction> {

    @Override
    public String getProvider() {
        return ActionTypeEnum.notify.name();
    }

    @Override
    public void applyRuleNode(NotifyAction notifyAction, RuleNodeModel nodeModel) {
        nodeModel.setExecutor(getProvider());
        nodeModel.setConfiguration(notifyAction.convertToMap());
    }

    @Override
    public List<String> parseColumns(NotifyAction config) {
        return config.parseColumns();
    }
}
