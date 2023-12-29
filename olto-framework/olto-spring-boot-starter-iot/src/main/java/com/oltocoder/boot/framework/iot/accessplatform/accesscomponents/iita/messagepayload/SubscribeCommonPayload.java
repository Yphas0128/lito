package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload;

import lombok.Data;

@Data
public class SubscribeCommonPayload {
    private String id;
    private String method;
    private Object params;
}
