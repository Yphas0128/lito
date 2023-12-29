package com.oltocoder.boot.framework.iot.accessplatform.data;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class OnlineData {
    private String accessPlatformName;
    private String deviceId;
    private Map<String, String> extraProperties;

    public String getExtraPropertyValue(String propertyName, String defaultValue) {
        if (extraProperties == null)
            return defaultValue;
        if (!extraProperties.containsKey(propertyName))
            return defaultValue;
        return extraProperties.get(propertyName);
    }
}
