package com.oltocoder.boot.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.oltocoder.boot.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ActionTypeEnum implements IntArrayValuable {


    device(0,"设备"),
    alarm(1, "报警"),
    notify(2,"通知"),
    delay(3,"延迟");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ActionTypeEnum::getValue).toArray();
    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;


    public static ActionTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(actionType -> actionType.getValue().equals(value), ActionTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}