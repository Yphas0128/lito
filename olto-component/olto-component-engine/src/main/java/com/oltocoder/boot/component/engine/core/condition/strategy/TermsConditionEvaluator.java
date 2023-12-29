package com.oltocoder.boot.component.engine.core.condition.strategy;

import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.consts.Reactors;
import com.oltocoder.boot.component.engine.core.condition.TermCondition;
import com.oltocoder.boot.component.engine.core.entity.param.Term;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public class TermsConditionEvaluator implements ConditionEvaluatorStrategy {

    public static final String TYPE = "terms";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean evaluate(TermCondition condition, RuleData context) {
        return false;
    }

    // todo 未完待续
    @Override
    public Function<RuleData, Mono<Boolean>> prepare(TermCondition condition) {
        List<Term> terms = condition.getTerms();

        return ruleData -> Reactors.ALWAYS_TRUE;
    }
}
