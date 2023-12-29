package com.oltocoder.boot.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.oltocoder.boot.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum FrequencyEnum  implements IntArrayValuable {

    ONE_SECOND(1, "0/1 * * * * ?","one_second"),
    FIVE_SECOND(5, "0/5 * * * * ?","five_second"),
    TEN_SECOND(10, "0/10 * * * * ?","ten_second");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(FrequencyEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String cron;

    private final String name;

    public static FrequencyEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(frequency -> frequency.getValue().equals(value), FrequencyEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
