package com.oltocoder.boot.component.engine.core.internal.triggers.impl;

import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.triggers.SceneTriggerProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.TimerTaskExecutorProvider;
import com.oltocoder.boot.framework.common.enums.TriggerTypeEnum;

public class TimerTriggerProvider implements SceneTriggerProvider<TimerTrigger> {

    @Override
    public String getProvider() {
        return TriggerTypeEnum.timer.name();
    }

    @Override
    public void applyRuleNode(TimerTrigger trigger, RuleModel model, RuleNodeModel sceneNode) {
        RuleNodeModel timerNode = new RuleNodeModel();

        timerNode.setId("scene:timer");
//        timerNode.setName("定时触发场景");
        timerNode.setExecutor(TimerTaskExecutorProvider.EXECUTOR);
        timerNode.setConfiguration(trigger.getTimer().convertToMap());
        model.getNodes().add(timerNode);

        //定时->场景
        model.link(timerNode, sceneNode);
    }

    @Override
    public TimerTrigger newConfig() {
        return new TimerTrigger();
    }
}
