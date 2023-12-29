package com.oltocoder.boot.framework.iot.accessplatform.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "olto.iot")
@Data
public class AccessPlatformListProperties {

    private Map<String, AccessPlatformProperties> accessPlatforms;

}
