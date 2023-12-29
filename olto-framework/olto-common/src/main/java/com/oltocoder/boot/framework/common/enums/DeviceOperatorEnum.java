package com.oltocoder.boot.framework.common.enums;

import cn.hutool.core.util.ArrayUtil;
import com.oltocoder.boot.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


//关于触发类型
@Getter
@AllArgsConstructor
public enum DeviceOperatorEnum implements IntArrayValuable {

    online(0,"设备上线"),
    offline(1,"设备下线"),
    reportEvent(2,"事件上报"),
    reportProperty(3,"属性上报"),
    readProperty(4,"读取属性"),
    writeProperty(5,"修改属性"),
    invokeFunction(6,"调用功能");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DeviceOperatorEnum::getValue).toArray();
    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;


    public static DeviceOperatorEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(deviceOperator -> deviceOperator.getValue().equals(value), DeviceOperatorEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
