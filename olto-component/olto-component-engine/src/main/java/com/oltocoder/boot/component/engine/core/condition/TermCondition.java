package com.oltocoder.boot.component.engine.core.condition;

import com.oltocoder.boot.component.engine.core.entity.param.Term;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @title: Condition
 * @Author Ypier
 * @Date: 2023/9/2 15:15
 */
@Getter
@Setter
public class TermCondition {

    /**
     * 条件类型,不同的条件类型支持不同的处理方式,由具体的规则引擎实现
     */
    private String type;

    /**
     * 条件配置,不同的条件类型配置不同
     */
    private List<Term> terms;
}
