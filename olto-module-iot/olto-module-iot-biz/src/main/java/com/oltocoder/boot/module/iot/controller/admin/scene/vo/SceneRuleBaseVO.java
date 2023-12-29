package com.oltocoder.boot.module.iot.controller.admin.scene.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class SceneRuleBaseVO {

    @Schema(description = "告警名称")
    @NotBlank(message = "告警名称不可为空")
    private String name;

    @Schema(description = "触发方式")
    @NotNull(message = "error.scene_rule_trigger_cannot_be_null")
    private Integer triggerType ;
}
