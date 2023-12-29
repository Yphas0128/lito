package com.oltocoder.boot.component.core.metadata.types;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntType extends NumberType<Integer> {

    public static final String Identifier= "int";

    private static final int SCALE = Integer.getInteger("iotak.type.int.scale", 0);
    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public String getName() {
        return "整型";
    }

    @Override
    protected int defaultScale() {
        return SCALE;
    }

    @Override
    protected Integer castNumber(Number number) {
        return number.intValue();
    }


}
