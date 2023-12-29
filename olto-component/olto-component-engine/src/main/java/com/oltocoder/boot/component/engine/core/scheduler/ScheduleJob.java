package com.oltocoder.boot.component.engine.core.scheduler;

import cn.hutool.core.map.MapUtil;
import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @title: Job
 * @Author Ypier
 * @Date: 2023/9/2 14:50
 */
@Getter
@Setter
public class ScheduleJob {

    /**
     * 规则实例ID
     */
    private String instanceId;

    /**
     * 规则ID
     */
    private String ruleId;

    /**
     *  节点ID
     */
    private String nodeId;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 模型类型
     */
    private String modelType;

    /**
     * 执行器
     */
    private String executor;

    /**
     * 执行器配置信息
     */
    private Map<String, Object> configuration;

    /**
     * 输入节点
     *
     * @see RuleNodeModel#getId()
     * @see RuleNodeModel#getInputs()
     */
    private List<String> inputs = new ArrayList<>();

    /**
     * 输出节点
     */
    private List<Output> outputs = new ArrayList<>();

    public Optional<Object> getConfiguration(String key) {
        if(MapUtil.isEmpty(configuration)){
            return Optional.empty();
        }
        return Optional.ofNullable(configuration.get(key));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Output implements Serializable {
        /**
         * 输出节点
         *
         * @see RuleNodeModel#getId()
         */
        private String output;

        /**
         * 输出条件,满足条件才输出
         */
        private TermCondition termCondition;
    }
}
