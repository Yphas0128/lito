package com.oltocoder.boot.component.engine.core.engine;

import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.task.Task;
import reactor.core.publisher.Flux;

public interface RuleEngine {

    // 启动规则
    Flux<Task> startRule(String instanceId, RuleModel rule);



}
