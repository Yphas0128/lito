package com.oltocoder.boot.component.engine.core.condition.eva;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.consts.Reactors;
import com.oltocoder.boot.component.engine.core.condition.strategy.ConditionEvaluatorStrategy;
import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * @title:
 * @Author Ypier
 * @Date: 2023/9/3 19:08
 */
public class DefaultConditionEvaluator implements ConditionEvaluator {

    private final Map<String, ConditionEvaluatorStrategy> allStrategy = new HashMap<>();

    @Override
    public boolean evaluate(TermCondition condition, RuleData ruleData) {
        if (condition == null || StringUtils.isEmpty(condition)) {
            return true;
        }
        return Optional.ofNullable(allStrategy.get(condition.getType()))
                .map(strategy -> strategy.evaluate(condition, ruleData))
                .orElseThrow(() -> new UnsupportedOperationException("不支持的条件类型:" + condition.getType()));
    }

    @Override
    public Function<RuleData, Mono<Boolean>> prepare(TermCondition condition) {
        if (condition == null || StringUtils.isEmpty(condition.getTerms())) {
            return ignore -> Reactors.ALWAYS_TRUE;
        }
        return Optional
                .ofNullable(allStrategy.get(condition.getType()))
                .map(strategy -> strategy.prepare(condition))
                .orElseThrow(() -> new UnsupportedOperationException("不支持的条件类型:" + condition.getType()));
    }

    public void register(ConditionEvaluatorStrategy strategy) {
        allStrategy.put(strategy.getType(), strategy);
    }
}
