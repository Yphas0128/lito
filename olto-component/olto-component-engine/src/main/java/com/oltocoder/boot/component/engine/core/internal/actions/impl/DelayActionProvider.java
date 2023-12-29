package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneActionProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.DelayTaskExecutorProvider;
import com.oltocoder.boot.framework.common.enums.ActionTypeEnum;

import java.util.Collections;
import java.util.List;

/**
 * @title: 延迟
 * @Author Ypier
 * @Date: 2023/9/2 14:20
 */
public class DelayActionProvider  implements SceneActionProvider<DelayAction> {
    @Override
    public String getProvider() {
        return ActionTypeEnum.delay.name();
    }

    @Override
    public void applyRuleNode(DelayAction delay, RuleNodeModel nodeModel) {
        DelayTaskExecutorProvider.DelayTaskExecutorConfig config = new DelayTaskExecutorProvider.DelayTaskExecutorConfig(
                DelayTaskExecutorProvider.PauseType.delay,delay.getTime(),delay.getUnit().getChronoUnit());
        nodeModel.setExecutor(getProvider());
        nodeModel.setConfiguration(config.convertToMap());
    }

    @Override
    public List<String> parseColumns(DelayAction config) {
        return Collections.emptyList();
    }
}
