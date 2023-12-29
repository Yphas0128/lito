package com.oltocoder.boot.component.engine.core.entity.scene;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneAction;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SceneActions implements Serializable {

    /**
     * 是否并行执行动作
     */
    private boolean parallel;

    /**
     * 执行动作
     */
    private List<SceneAction> actions;

    public List<String> createContextColumns() {
        List<String> contextTerm = new ArrayList<>();
        if (CollUtil.isNotEmpty(actions)){
            for (SceneAction action : actions) {
                contextTerm.addAll(action.createContextColumns());
            }
        }
        return contextTerm;
    }
}
