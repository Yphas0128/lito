package com.oltocoder.boot.component.engine.core.rule;

import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * 规则连线
 */
@Getter
@Setter
public class RuleLink {
    /**
     * 连线ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 连线的源节点
     */
    private RuleNodeModel source;

    /**
     * 连线的目标节点
     */
    private RuleNodeModel target;

    /**
     * 条件,source节点输出的数据需要满足条件才会输出到target时
     */
    private TermCondition termCondition;
}
