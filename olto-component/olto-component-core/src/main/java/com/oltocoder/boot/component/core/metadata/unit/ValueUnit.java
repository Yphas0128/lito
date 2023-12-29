package com.oltocoder.boot.component.core.metadata.unit;

public interface ValueUnit {


    String getId();

    /**
     * 名称
     *
     * @return 名称
     */
    String getName();

    /**
     * 说明
     *
     * @return 说明
     */
    String getDescription();
    /**
     * 单位符号
     * @return 符号
     */
    String getSymbol();

    /**
     * 对值进行格式化
     *
     * @param value 值
     * @return 格式化后的值
     */
    Object format(Object value);
}
