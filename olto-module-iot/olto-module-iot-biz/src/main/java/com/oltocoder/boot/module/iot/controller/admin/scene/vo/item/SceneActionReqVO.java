package com.oltocoder.boot.module.iot.controller.admin.scene.vo.item;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SceneActionReqVO {

    @Schema(description = "执行器类型")
    @NotNull
    private String execType;



}
