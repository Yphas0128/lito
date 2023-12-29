package com.oltocoder.boot.component.engine.core.entity.scene;

import cn.hutool.core.collection.CollUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.oltocoder.boot.component.engine.core.entity.param.Term;
@Getter
@Setter
public class SceneConditionAction implements Serializable {

    /**
     * 条件
     */
    private List<Term> when;

    /**
     *满足条件时执行的动作
     */
    private List<SceneActions> then;

    public Collection<? extends Term> createContextTerm() {
        List<Term> contextTerm = new ArrayList<>();

        if(CollUtil.isNotEmpty(then)){
            for (SceneActions sceneActions : then){
                List<Term> list = sceneActions.createContextColumns()
                        .stream()
                        .map(column -> {
                            Term term = new Term();
                            term.setColumn(column);
                            return term;
                        })
                        .collect(Collectors.toList());

                contextTerm.addAll(list);
            }
        }
        if (CollUtil.isNotEmpty(when)) {
            contextTerm.addAll(when);
        }
        return contextTerm;
    }
}
