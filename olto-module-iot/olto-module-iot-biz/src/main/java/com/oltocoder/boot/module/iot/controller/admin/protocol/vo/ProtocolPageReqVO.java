package com.oltocoder.boot.module.iot.controller.admin.protocol.vo;

import com.oltocoder.boot.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProtocolPageReqVO extends PageParam {

    @Schema(description = "协议名称")
    private String name;
}
