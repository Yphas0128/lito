package com.oltocoder.boot.component.engine.core.internal.actions;

import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;

import java.util.List;

/**
 * @title: SceneActionProvider
 * @Author Ypier
 * @Date: 2023/9/2 13:41
 */
public interface SceneActionProvider<C> {

    String getProvider();

    void applyRuleNode(C actionConfig, RuleNodeModel nodeModel);

    List<String> parseColumns(C config);
}
