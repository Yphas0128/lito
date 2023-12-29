package com.oltocoder.boot.framework.iot.accessplatform.data;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Data
@Builder
public class ReportData {
    private String deviceId;
    private Date collectTime;
    private Map<String,Object> data;
}
