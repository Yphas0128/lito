package com.oltocoder.boot.component.engine.core.condition.strategy;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface ConditionEvaluatorStrategy {


    String getType();

    boolean evaluate(TermCondition condition, RuleData context);

    /**
     * prepare
     * @param condition prepare
     * @return prepare
     */
    default Function<RuleData, Mono<Boolean>> prepare(TermCondition condition) {
        return (data) -> Mono.just(evaluate(condition, data));
    }
}
