package com.oltocoder.boot.framewwork.iot.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformListProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = AccessPlatformPropertiesTest.class)
@ActiveProfiles("unit-test")
@EnableConfigurationProperties({AccessPlatformListProperties.class})
public class AccessPlatformPropertiesTest {

    @Resource
    private AccessPlatformListProperties accessPlatformProperties;


    @Test
    public void testReadPropertiesFromApplicationYaml() {
        Assertions.assertNotNull(accessPlatformProperties.getAccessPlatforms(), "accessPlatformProperties读取为空");
    }


}
