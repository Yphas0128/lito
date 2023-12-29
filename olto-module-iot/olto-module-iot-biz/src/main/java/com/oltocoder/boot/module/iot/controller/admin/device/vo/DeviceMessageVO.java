package com.oltocoder.boot.module.iot.controller.admin.device.vo;

import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.oltocoder.boot.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static com.oltocoder.boot.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

@Data
public class DeviceMessageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "值")
    private String data;

    @Schema(description = "时间")
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
    private LocalDateTime now;

    @Schema(description = "状态")
    private boolean status;

    @Schema(description = "类型")
    private String messageType;


    public String toJsonStr(){
        return JsonUtils.toJsonString(this);
    }
}
