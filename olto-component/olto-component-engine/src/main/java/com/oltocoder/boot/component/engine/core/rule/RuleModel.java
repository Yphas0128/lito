package com.oltocoder.boot.component.engine.core.rule;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RuleModel {

    /**
     * 规则模型ID
     */
    private String id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 模型类型
     */
    private String type;

    /**
     * 规则事件连接
     */
    private List<RuleLink> events = new ArrayList<>();

    /**
     * 规则节点,包含所有的节点
     */
    private List<RuleNodeModel> nodes = new ArrayList<>();

    public RuleLink link(RuleNodeModel source, RuleNodeModel target) {

        RuleLink link = new RuleLink();
        link.setId(source.getId() + ":" + target.getId());

        link.setSource(source);
        link.setTarget(target);

        source.getOutputs().add(link);
        target.getInputs().add(link);

        return link;
    }
}
