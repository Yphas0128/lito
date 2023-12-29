package com.oltocoder.boot.component.engine.core.entity.value;

import cn.hutool.core.bean.BeanUtil;
import com.oltocoder.boot.component.engine.core.entity.param.Term;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class TermValue implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 来源
     */
    public Source source;

    /**
     * source为manual时 不可为空
     */
    private Object value;

    /**
     * source为metric 不可为空
     */
    private String metric;

    public static TermValue manual(Object value) {
        TermValue termValue = new TermValue();
        termValue.setValue(value);
        termValue.setSource(Source.manual);
        return termValue;
    }

    public static TermValue metric(String metric) {
        TermValue termValue = new TermValue();
        termValue.setMetric(metric);
        termValue.setSource(Source.metric);
        return termValue;
    }

    public static List<TermValue> of(Term term) {
        return of(term.getValue());
    }

    public static List<TermValue> of(Object value) {
        if (value == null) {
            return Collections.emptyList();
        }
        if (value instanceof Map) {
            return Collections.singletonList(BeanUtil.copyProperties(value, TermValue.class));
        }
        if (value instanceof TermValue) {
            return Collections.singletonList(((TermValue) value));
        }
        if (value instanceof Collection) {
            return ((Collection<?>) value)
                    .stream()
                    .flatMap(val -> of(val).stream())
                    .collect(Collectors.toList());
        }
        return Collections.singletonList(TermValue.manual(value));
    }

    public enum Source {
        manual,
        metric,
        variable,
        upper
    }

}
