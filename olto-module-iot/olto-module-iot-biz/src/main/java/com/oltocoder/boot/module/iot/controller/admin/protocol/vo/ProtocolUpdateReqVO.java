package com.oltocoder.boot.module.iot.controller.admin.protocol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProtocolUpdateReqVO extends ProtocolBaseVO{

    @Schema(description = "主键")
    @NotNull
    private Long id;
}
