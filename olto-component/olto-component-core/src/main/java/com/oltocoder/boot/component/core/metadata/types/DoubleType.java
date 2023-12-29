package com.oltocoder.boot.component.core.metadata.types;

public class DoubleType extends NumberType<Double> {

    public static final String Identifier = "double";

    private static final int SCALE = Integer.getInteger("iotak.type.int.scale", 2);
    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public String getName() {
        return "双精度浮点数";
    }

    @Override
    protected int defaultScale() {
        return SCALE;
    }

    @Override
    protected Double castNumber(Number number) {
         return number.doubleValue();
    }
}
