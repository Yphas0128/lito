package com.oltocoder.boot.component.engine.core.internal.triggers;

import com.oltocoder.boot.component.engine.core.entity.param.Term;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.triggers.impl.DeviceTrigger;
import com.oltocoder.boot.component.engine.core.internal.triggers.impl.TimerTrigger;
import com.oltocoder.boot.component.engine.core.internal.SceneProviderFactory;
import com.oltocoder.boot.framework.common.enums.TriggerTypeEnum;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class Trigger {
    /**
     * 类型
     */
    private String type;
    /**
     * 设备触发
     */
    private DeviceTrigger device;
    /**
     * 定时触发
     */
    private TimerTrigger timer;

    public void applyModel(RuleModel model, RuleNodeModel sceneNode) {
        provider().applyRuleNode(triggerConfig(), model, sceneNode);
    }

    private ITriggerConfig triggerConfig() {
        switch (TriggerTypeEnum.valueOf(type)){
            case device:
                return device;
            case timer:
                return timer;
            default:
                ITriggerConfig config = provider().newConfig();
                return config;
        }
    }


    private SceneTriggerProvider<ITriggerConfig> provider() {
        return SceneProviderFactory.getTriggerProviderNow(type);
    }


    /**
     * todo 重构查询条件,替换为实际将要输出的变量.
     * @param terms  条件
     * @return 重构后的条件
     */
    public List<Term> refactorTerm(String tableName, List<Term> terms) {
        if (CollectionUtils.isEmpty(terms)) {
            return terms;
        }

        List<Term> target = new ArrayList<>(terms.size());
        for (Term term : terms) {
            Term copy = term.clone();

            target.add(refactorTermValue(tableName, copy));
        }

        return target;
    }

    // todo 未完待续
    private Term refactorTermValue(String tableName, Term term) {
        if (term.getColumn() == null) {
            return term;
        }

        return term;
    }
}
