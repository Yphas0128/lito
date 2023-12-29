package com.oltocoder.boot.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.oltocoder.boot.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SignFieldEnum implements IntArrayValuable {

    SIGN_BYTE(1, "byte"),
    SHORT_BYTE(2, "short"),
    SIGN_INT(3, "int"),
    SIGN_LONG(4, "long"),
    SIGN_FLOAT(5, "float"),
    SIGN_DOUBLE(6, "double"),
    SIGN_BOOLEAN(7, "boolean");



    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(SignFieldEnum::getValue).toArray();

    public static SignFieldEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(signField -> signField.getValue().equals(value), SignFieldEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
