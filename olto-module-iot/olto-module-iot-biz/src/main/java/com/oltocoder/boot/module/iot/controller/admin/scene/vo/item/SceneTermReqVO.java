package com.oltocoder.boot.module.iot.controller.admin.scene.vo.item;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SceneTermReqVO {
    /**
     * 字段
     */
    @Schema(description = "字段名")
    private String column;

    /**
     * 值
     */
    @Schema(description = "条件值")
    private Object value;


    @Schema(description = "")
    private String comparator;


    @Schema(description = "多个条件关联类型",defaultValue = "and")
    private Type type = Type.and;


    public enum Type {
        or, and;
    }
}
