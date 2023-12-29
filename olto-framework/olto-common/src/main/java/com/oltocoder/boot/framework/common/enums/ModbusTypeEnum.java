package com.oltocoder.boot.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.oltocoder.boot.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ModbusTypeEnum implements IntArrayValuable {

    COILS(1, "线圈寄存器","01"),
    INPUTS(2, "离散输入寄存器","02"),
    HOLD_REGISTER(3, "保存寄存器","03"),
    INPUT_REGISTER(4, "输入寄存器","04");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ModbusTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    private final String code;

    public static ModbusTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(modbusType -> modbusType.getValue().equals(value), ModbusTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
