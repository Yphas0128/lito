package com.oltocoder.boot.framework.iot.accessplatform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class OnlineExtraPropertyDefine {
    private String PropertyShowName;
    private String PropertyName;
    private String PropertyType;
    private String Description;
    private boolean Required;
}
