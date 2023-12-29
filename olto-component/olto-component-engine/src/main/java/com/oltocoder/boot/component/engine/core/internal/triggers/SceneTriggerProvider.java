package com.oltocoder.boot.component.engine.core.internal.triggers;

import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;

public interface SceneTriggerProvider<C extends ITriggerConfig> {

    //
    String getProvider();

   default void applyRuleNode(C triggerConfig, RuleModel model, RuleNodeModel sceneNode){

   }

    C newConfig();
}
