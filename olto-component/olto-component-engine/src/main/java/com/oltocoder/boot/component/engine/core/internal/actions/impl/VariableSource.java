package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import cn.hutool.core.bean.BeanUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 变量值来源描述
 */
@Getter
@Setter
public class VariableSource  implements Serializable {

    /**
     * 来源
     */
    private Source source;

    /**
     * 固定值,[source]为[fixed]时不能为空
     */
    private Object value;

    /**
     * 上游key,[source]为[upper]时不能为空
     */
    private String upperKey;

    /**
     *关系,[source]为[relation]时不能为空
     */
//    private VariableObjectSpec relation;

    /**
     * 拓展信息
     */
    private Map<String, Object> options;

    public static VariableSource of(Object value) {
        if (value instanceof VariableSource) {
            return ((VariableSource) value);
        }
        if(value instanceof Map){
            Map<?, ?> mapVal = ((Map<?, ?>) value);
            Object sourceName = mapVal.get("source");
            if(sourceName != null && VariableSource.Source.of(String.valueOf(sourceName)).isPresent()){
                VariableSource source = convertToObj(mapVal);
                if (source.getSource() != null) {
                    return source;
                }
            }
        }
        return fixed(value);
    }

    private static VariableSource fixed(Object value) {
        VariableSource variableSource = new VariableSource();
        variableSource.setSource(Source.fixed);
        variableSource.setValue(value);
        return variableSource;
    }

    private  static VariableSource convertToObj(Map<?,?> mapVal) {
        return BeanUtil.toBean(mapVal, VariableSource.class);
    }

    public Map<String, Object> toMap() {
        return BeanUtil.beanToMap(this,false,true);
    }

    public enum Source {
        //固定值
        fixed,
        //来自上游
        upper,
        //通过关系选择
        relation;

        public static Optional<Source> of(String source) {
            for (Source value : values()) {
                if (value.name().equals(source)) {
                    return java.util.Optional.of(value);
                }
            }
            return java.util.Optional.empty();
        }
    }
}
