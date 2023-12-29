package com.oltocoder.boot.module.iot.service.scene;


import com.oltocoder.boot.component.bus.config.EventBusConfiguration;
import com.oltocoder.boot.component.engine.config.RuleEngineConfiguration;
import com.oltocoder.boot.component.engine.core.engine.DefaultRuleEngine;
import com.oltocoder.boot.component.engine.core.engine.RuleEngine;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.entity.scene.SceneRule;
import com.oltocoder.boot.module.iot.base.BaseDbUnitTest;
import com.oltocoder.boot.module.iot.dal.dataobject.scene.SceneDO;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.Optional;

@Import({SceneServiceImpl.class, RuleEngineConfiguration.class, EventBusConfiguration.class})
public class SceneTest extends BaseDbUnitTest {

    @Resource
    private SceneServiceImpl sceneService;

    @Resource
    private DefaultRuleEngine ruleEngine;


    @Test
    public void test() {
        SceneDO scene = sceneService.getScene(1L);

        SceneRule rule = new SceneRule();
        rule.setId(scene.getId());
        rule.setName(scene.getName());

        Optional.ofNullable(scene.getSceneTrigger()).ifPresent( trigger -> rule.parseTrigger(trigger));
        Optional.ofNullable(scene.getBranches()).ifPresent(branches-> rule.ParseBranches(branches));
        Optional.ofNullable(scene.getConfiguration()).ifPresent(configuration-> rule.parseConfiguration(configuration));
        RuleModel model = rule.toModel();

        ruleEngine.startRule("1", model);

        System.out.println(rule);
    }

    @Test
    public void testFlux() {

        Flux.defer(()-> Flux.just("a")).concat(Flux.just("a"));
        Flux.just("b").concat(Flux.just("a"))
                .subscribe();
    }

}
