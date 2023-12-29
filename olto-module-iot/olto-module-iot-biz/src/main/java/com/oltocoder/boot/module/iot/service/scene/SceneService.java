package com.oltocoder.boot.module.iot.service.scene;

import com.oltocoder.boot.module.iot.controller.admin.scene.vo.SceneRuleCreateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.scene.SceneDO;

import java.util.List;

public interface SceneService {
    Long createScene(SceneRuleCreateReqVO reqVO);

    void enableScene(Long id);

    SceneDO getScene(Long id);

    List<SceneDO> getScenes();
}
