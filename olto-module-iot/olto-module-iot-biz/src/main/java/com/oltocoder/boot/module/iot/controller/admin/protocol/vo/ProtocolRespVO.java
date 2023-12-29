package com.oltocoder.boot.module.iot.controller.admin.protocol.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProtocolRespVO extends ProtocolBaseVO{

    @Schema(description = "主键Id")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态")
    private Integer status;
}
