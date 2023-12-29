package com.oltocoder.boot.component.core.metadata.types;

import com.oltocoder.boot.component.core.metadata.Converter;
import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.metadata.UnitSupported;
import com.oltocoder.boot.component.core.metadata.unit.ValueUnit;
import com.oltocoder.boot.framework.common.util.number.NumberUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.function.Function;

@Getter
@Setter
public abstract class NumberType<N extends Number> extends AbstractDataType<NumberType<N>> implements UnitSupported, DataType, Converter<N> {

    //最大值
    private Number max;

    //最小值
    private Number min;

    //单位
    private ValueUnit unit;

    // 几位小数
    private Integer scale;

    public NumberType<N> max(Number max) {
        this.max = max;
        return this;
    }

    public NumberType<N> min(Number min) {
        this.min = min;
        return this;
    }

    public NumberType<N> unit(ValueUnit unit) {
        this.unit = unit;
        return this;
    }


    public Integer getScale(Integer defaultValue) {
        return this.scale == null ? defaultValue : this.scale;
    }
    @Override
    public final N convert(Object value) {
        if (value instanceof Number) {
            Number number = ((Number) value);
            //如果传入的是整数,或者未设置精度,则直接返回
            if (NumberUtils.isIntNumber(number)) {
                return castNumber(number);
            }
        }
        return this.convertScaleNumber(value, this.getScale(defaultScale()));
    }

    public final N convertScaleNumber(Object value,
                                      Integer scale) {
        return convertScaleNumber(value, scale, this::castNumber);
    }

    public static <T> T convertScaleNumber(Object value,
                                           Integer scale,
                                           Function<Number, T> mapper) {
        BigDecimal decimal;
        if (value instanceof Number && scale == null) {
            return mapper.apply(((Number) value));
        }
        if (value instanceof String) {
            try {
                value = new BigDecimal(((String) value));
            } catch (NumberFormatException e) {
                return null;
            }
        }

        if (value instanceof Date) {
            value = new BigDecimal(((Date) value).getTime());
        }
        if (!(value instanceof BigDecimal)) {
            try {
                decimal = new BigDecimal(String.valueOf(value));
            } catch (Throwable err) {
                return null;
            }
        } else {
            decimal = ((BigDecimal) value);
        }
        if (scale == null) {
            return mapper.apply(decimal);
        }
        return mapper.apply(decimal.setScale(scale, RoundingMode.valueOf("HALF_UP")));
    }

    @Override
    public Object format(Object value) {
        if (value == null) {
            return null;
        }
        Number val = convertScaleNumber(value,
                this.getScale(),
                Function.identity());
        if (val == null) {
            return String.valueOf(value);
        }
        String str;
        if (val instanceof BigDecimal) {
            str = ((BigDecimal) val).toPlainString();
        } else {
            str = String.valueOf(val);
        }
        ValueUnit unit = getUnit();
        if (unit == null) {
            return String.valueOf(str);
        }
        return unit.format(str);
    }

    protected abstract int defaultScale();

    protected abstract N castNumber(Number number);

}
