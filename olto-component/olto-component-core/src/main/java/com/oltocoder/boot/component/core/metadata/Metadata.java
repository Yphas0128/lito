package com.oltocoder.boot.component.core.metadata;

// 模型定义基础接口
public interface Metadata {

    /**
     * 标志符
     * @return
     */
    String getIdentifier();

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

    default void setDescription(String description) {

    }
}
