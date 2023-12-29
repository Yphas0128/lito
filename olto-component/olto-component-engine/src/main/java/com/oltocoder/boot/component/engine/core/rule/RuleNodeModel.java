package com.oltocoder.boot.component.engine.core.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RuleNodeModel {

    private String id;

    /**
     * 规则ID
     */
    private String ruleId;

    /**
     * 节点名称
     */
//    private String name;

    /**
     * 执行器标识
     */
    private String executor;
    /**
     * 配置信息,不同的执行器,配置信息不同
     */
    private Map<String, Object> configuration = new HashMap<>();

    /**
     * 此节点的输入节点
     */
    private List<RuleLink> inputs = new ArrayList<>();

    /**
     * 此节点的输出节点
     */
    private List<RuleLink> outputs = new ArrayList<>();

    public RuleNodeModel addConfiguration(String key, Object value) {
        configuration.put(key, value);
        return this;
    }
}
