package com.oltocoder.boot.component.core.metadata.types;

import lombok.Generated;

public class LongType extends NumberType<Long> {
    public static final String Identifier = "long";
    public static final LongType GLOBAL = new LongType();
    public static final int SCALE = Integer.getInteger("jetlinks.type.long.scale", 0);

    @Override
    @Generated
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    @Generated
    public String getName() {
        return "长整型";
    }

    @Override
    protected Long castNumber(Number number) {
        return number.longValue();
    }

    @Override
    public int defaultScale() {
        return SCALE;
    }
}
