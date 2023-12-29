package com.oltocoder.boot.component.core.metadata.types;

import cn.hutool.core.util.NumberUtil;
import com.oltocoder.boot.component.core.metadata.Converter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Setter
@Slf4j
public class DateTimeType  extends AbstractDataType<DateTimeType> implements Converter<Date> {

    public static final String Identifier = "date";
    public static final String TIMESTAMP_FORMAT = "timestamp";
    private String format = TIMESTAMP_FORMAT;


    public DateTimeType timeZone(ZoneId zoneId) {
        this.zoneId = zoneId;

        return this;
    }

    private ZoneId zoneId = ZoneId.systemDefault();
    private DateTimeFormatter formatter;

    public DateTimeType format(String format) {
        this.format = format;
        this.getFormatter();
        return this;
    }

    protected DateTimeFormatter getFormatter() {
        if (formatter == null && !TIMESTAMP_FORMAT.equals(format)) {
            formatter = DateTimeFormatter.ofPattern(format);
        }
        return formatter;
    }

    @Override
    public Date convert(Object value) {
        if (value instanceof Instant) {
            return Date.from(((Instant) value));
        }
        if (value instanceof LocalDateTime) {
            return Date.from(((LocalDateTime) value).atZone(zoneId).toInstant());

        }

        if (value instanceof Date) {
            return ((Date) value);
        }
        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }
        if (value instanceof String) {
            if(NumberUtil.isNumber(String.valueOf(value))){
                return new Date(Long.parseLong((String) value));
            }
//            Date data = DateFormatter.fromString(((String) value));
//            if (data != null) {
//                return data;
//            }
            DateTimeFormatter formatter = getFormatter();
            if (null == formatter) {
                throw new IllegalArgumentException("unsupported date format:" + value);
            }
            return Date.from(LocalDateTime.parse(((String) value), formatter)
                    .atZone(zoneId)
                    .toInstant());
        }
        throw new IllegalArgumentException("can not format datetime :" + value);
    }


    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public String getName() {
        return "时间";
    }

    @Override
    public Object format(Object value) {
        return null;
    }
}
