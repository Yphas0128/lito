package com.oltocoder.boot.module.iot.service.scene;

import com.oltocoder.boot.component.engine.core.engine.RuleEngine;
import com.oltocoder.boot.module.iot.controller.admin.scene.vo.SceneRuleCreateReqVO;
import com.oltocoder.boot.module.iot.convert.scene.SceneConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.scene.SceneDO;
import com.oltocoder.boot.module.iot.dal.mysql.scene.SceneMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service("sceneService")
public class SceneServiceImpl implements SceneService {

    @Resource
    private SceneMapper sceneMapper;

    @Resource
    private RuleEngine ruleEngine;

    @Override
    public Long createScene(SceneRuleCreateReqVO reqVO) {
        return null;
    }

    @Override
    public void enableScene(Long id) {

    }

    @Override
    public SceneDO getScene(Long id) {
        return sceneMapper.selectById(id);
    }

    @PostConstruct
    public void run() throws Exception {
        List<SceneDO> res = getScenes();
        Flux.fromIterable(res)
                .flatMap(e->
                        Mono.defer(()-> ruleEngine.startRule(e.getId().toString(), SceneConvert.INSTANCE.convert(e).toModel()).then()))
                .subscribe();
    }

    @Override
    public List<SceneDO> getScenes() {
        return sceneMapper.selectList();
    }
}
