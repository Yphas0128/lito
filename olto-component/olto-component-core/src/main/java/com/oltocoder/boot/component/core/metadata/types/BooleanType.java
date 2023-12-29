package com.oltocoder.boot.component.core.metadata.types;

import com.oltocoder.boot.component.core.metadata.Converter;
import com.oltocoder.boot.component.core.metadata.DataType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanType extends AbstractDataType<BooleanType> implements DataType, Converter<Boolean> {

    public static final String Identifier = "boolean";
    public static final BooleanType GLOBAL = new BooleanType();

    private String trueText = "是";

    private String falseText = "否";

    private String trueValue = "true";

    private String falseValue = "false";

    @Override
    public Boolean convert(Object value) {
        if (value instanceof Boolean) {
            return ((Boolean) value);
        }

        String stringVal = String.valueOf(value).trim();
        if (stringVal.equals(trueValue) || stringVal.equals(trueText)) {
            return true;
        }

        if (stringVal.equals(falseValue) || stringVal.equals(falseText)) {
            return false;
        }
        return stringVal.equals("1")
                || stringVal.equals("true")
                || stringVal.equals("y")
                || stringVal.equals("yes")
                || stringVal.equals("ok")
                || stringVal.equals("是")
                || stringVal.equals("正常");
    }

    @Override
    public String format(Object value) {
        Boolean trueOrFalse = convert(value);

        if (Boolean.TRUE.equals(trueOrFalse)) {
            return trueText;
        }
        if (Boolean.FALSE.equals(trueOrFalse)) {
            return falseText;
        }
        return "未知:" + value;
    }


    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public String getName() {
        return "布尔值";
    }
}
