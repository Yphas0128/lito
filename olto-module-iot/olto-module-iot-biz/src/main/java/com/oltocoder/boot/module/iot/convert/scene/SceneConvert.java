package com.oltocoder.boot.module.iot.convert.scene;

import com.oltocoder.boot.component.engine.core.entity.scene.SceneRule;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.module.iot.dal.dataobject.scene.SceneDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper
public interface SceneConvert {

    SceneConvert INSTANCE = Mappers.getMapper(SceneConvert.class);

    default SceneRule convert(SceneDO scene){
        SceneRule rule = new SceneRule();
        rule.setId(scene.getId());
        rule.setName(scene.getName());

        Optional.ofNullable(scene.getSceneTrigger()).ifPresent(trigger -> rule.parseTrigger(trigger));
        Optional.ofNullable(scene.getBranches()).ifPresent(branches-> rule.ParseBranches(branches));
        Optional.ofNullable(scene.getConfiguration()).ifPresent(configuration-> rule.parseConfiguration(configuration));
        return rule;
    }
}
