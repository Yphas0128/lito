package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload;

import lombok.Data;

import java.util.Map;

@Data
public class ReportDataPayload {
    private String id;
    private long occur;
    private String method;
    private Map<String,Object> params;

    public ReportDataPayload(){
        this.method="thing.event.property.post";
    }
}
