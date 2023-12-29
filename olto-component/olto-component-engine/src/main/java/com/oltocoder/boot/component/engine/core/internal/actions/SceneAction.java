package com.oltocoder.boot.component.engine.core.internal.actions;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.AlarmAction;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.DelayAction;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.DeviceAction;
import com.oltocoder.boot.component.engine.core.internal.actions.impl.NotifyAction;
import com.oltocoder.boot.component.engine.core.entity.param.Term;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.SceneProviderFactory;
import com.oltocoder.boot.framework.common.enums.ActionTypeEnum;
import com.oltocoder.boot.framework.common.util.collection.CollectionUtils;
import lombok.Data;

import java.util.*;

@Data
public class SceneAction {

    /**
     * 执行器类型
     */
    private String executor;

    /**
     * 执行器类型为[notify]
     */
    private NotifyAction notify;

    /**
     * 执行器类型为[delay]
     */
    private DelayAction delay;

    /**
     * 执行器类型为[device]
     */
    private DeviceAction device;

    /**
     * 执行器类型为[alarm]
     */
    private AlarmAction alarm;

    /**
     * 输出过滤条件,串行执行动作时
     */
    private List<Term> terms;

    /**
     * 其他执行器配置
     */
    private Map<String, Object> configuration;

    /**
     * 拓展信息
     */
    private Map<String, Object> options;

    public void applyNode(RuleNodeModel nodeModel) {
        SceneProviderFactory
                .getActionProviderNow(executor)
                .applyRuleNode(actionConfig(), nodeModel);
    }

    private Object actionConfig() {

        switch (ActionTypeEnum.valueOf(executor)) {
            case device:
                return device;
            case notify:
                return notify;
            case delay:
                return delay;
            case alarm:
                return alarm;
            default:
                return null; //todo throws
        }
    }

    public Collection<String> createContextColumns() {
        List<String> terms = new ArrayList<>();
        terms.addAll(parseColumnFromOptions(options));
        terms.addAll(parseActionTerms());
        return terms;
    }


    private List<String> parseColumnFromOptions(Map<String, Object> options) {
        Object columns;
        if (CollUtil.isEmpty(options) || (columns = options.get("columns")) == null) {
            return Collections.emptyList();
        }

        return CollectionUtils.convertToList(columns,String::valueOf);
    }

    /**
     * 尝试从动作的变量中提取出需要动态获取的列信息
     * @return 条件
     */
    private List<String> parseActionTerms() {
        return SceneProviderFactory
                .getActionProviderNow(executor)
                    .parseColumns(actionConfig());
    }
}
