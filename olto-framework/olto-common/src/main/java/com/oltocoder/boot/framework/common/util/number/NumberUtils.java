package com.oltocoder.boot.framework.common.util.number;

import cn.hutool.core.util.StrUtil;

import java.math.BigInteger;

/**
 * 数字的工具类，补全 {@link cn.hutool.core.util.NumberUtil} 的功能
 *
 * @author 芋道源码
 */
public class NumberUtils {

    public static Long parseLong(String str) {
        return StrUtil.isNotEmpty(str) ? Long.valueOf(str) : null;
    }

    public static boolean isIntNumber(Number number) {
        return number instanceof Integer ||
                number instanceof Long ||
                number instanceof Byte ||
                number instanceof Short ||
                number instanceof BigInteger;
    }
}
