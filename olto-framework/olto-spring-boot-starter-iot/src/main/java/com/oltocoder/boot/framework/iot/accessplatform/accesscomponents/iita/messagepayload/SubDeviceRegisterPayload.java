package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.messagepayload;

import lombok.Data;

@Data
public class SubDeviceRegisterPayload {

    private String id;
    private String method;
    private BizParams params;

    public SubDeviceRegisterPayload(){
        this.method="";
    }

    @Data
    public static class BizParams {
        private String productKey;
        private String deviceName;
        private String model;
    }
}
