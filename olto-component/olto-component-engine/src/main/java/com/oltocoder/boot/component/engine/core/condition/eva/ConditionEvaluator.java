package com.oltocoder.boot.component.engine.core.condition.eva;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @title: JS条件比较
 * @Author Ypier
 * @Date: 2023/9/3 19:02
 */
public interface ConditionEvaluator {

    /**
     * 执行并返回是否满足条件
     * @param termCondition 条件
     * @param context 规则数据
     * @return 是否满足条件
     */
    boolean evaluate(TermCondition termCondition, RuleData context);

    default Function<RuleData, Mono<Boolean>> prepare(TermCondition termCondition) {
        return (data) -> Mono.just(evaluate(termCondition, data));
    }
}
