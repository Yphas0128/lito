package com.oltocoder.boot.component.core.metadata.types;

public class FloatType extends NumberType<Float> {
    public static final String Identifier="float";

    private static final int SCALE=Integer.getInteger("iotak.type.float.scale",2);

    @Override
    protected Float castNumber(Number number){
        return number.floatValue();
    }

    @Override
    public int defaultScale(){
        return SCALE;
    }

    @Override
    public String getIdentifier(){
        return Identifier;
    }

    @Override
    public String getName(){
        return "单精度浮点数";
    }

}
