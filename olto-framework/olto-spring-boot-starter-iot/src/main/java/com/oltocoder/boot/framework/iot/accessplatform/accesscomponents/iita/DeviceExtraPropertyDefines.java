package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

import com.oltocoder.boot.framework.iot.accessplatform.OnlineExtraPropertyDefine;
import com.oltocoder.boot.framework.iot.accessplatform.OnlineExtraPropertyTypes;

import java.util.ArrayList;
import java.util.List;

public class DeviceExtraPropertyDefines {
    private static List<OnlineExtraPropertyDefine> extraPropertyDefines;

    static {
        extraPropertyDefines = new ArrayList<>();
        extraPropertyDefines.add(new OnlineExtraPropertyDefine("产品key", "product-key", OnlineExtraPropertyTypes.STRING, "产品key", true));
        extraPropertyDefines.add(new OnlineExtraPropertyDefine("设备名", "device-name", OnlineExtraPropertyTypes.STRING, "设备名", true));
    }

    public static List<OnlineExtraPropertyDefine> getExtraPropertyDefines() {
        return extraPropertyDefines;
    }
}
