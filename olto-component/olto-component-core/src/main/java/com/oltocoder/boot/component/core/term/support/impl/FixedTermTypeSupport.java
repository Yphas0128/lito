package com.oltocoder.boot.component.core.term.support.impl;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.term.support.TermTypeSupport;
import com.oltocoder.boot.component.core.metadata.types.*;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
public enum FixedTermTypeSupport implements TermTypeSupport {

    eq("等于", "eq"),
    neq("不等于", "neq"),
    gt("大于", "gt", DateTimeType.Identifier, IntType.Identifier, LongType.Identifier, FloatType.Identifier, DoubleType.Identifier),
    gte("大于等于", "gte", DateTimeType.Identifier, IntType.Identifier, LongType.Identifier, FloatType.Identifier, DoubleType.Identifier),
    lt("小于", "lt", DateTimeType.Identifier, IntType.Identifier, LongType.Identifier, FloatType.Identifier, DoubleType.Identifier),
    lte("小于等于", "lte", DateTimeType.Identifier, IntType.Identifier, LongType.Identifier, FloatType.Identifier, DoubleType.Identifier);

    private final String text;
    private final Set<String> supportTypes;

    private final String function;

    private FixedTermTypeSupport(String text, String function, String... supportTypes) {
        this.text = text;
        this.function = function;
        this.supportTypes = new HashSet<>(Arrays.asList(supportTypes));
    }


    @Override
    public String getType() {
        return name();
    }

    @Override
    public String getName() {
        return "message.term_type_" + name();
    }

    @Override
    public boolean isSupported(DataType type) {
        return supportTypes.isEmpty() || supportTypes.contains(type.getType());
    }
}
