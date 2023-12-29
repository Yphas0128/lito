package com.oltocoder.boot.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.oltocoder.boot.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TriggerTypeEnum implements IntArrayValuable {


    device(0,"设备"),
    manual(1,"手动"),
    timer(2,"定时");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TriggerTypeEnum::getValue).toArray();
    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;


    public static TriggerTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(triggerType -> triggerType.getValue().equals(value), TriggerTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
