package com.oltocoder.boot.framewwork.iot.accessplatform;

import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.accessplatform.AccessComponentFactory;
import com.oltocoder.boot.framework.iot.accessplatform.AccessMananger;
import com.oltocoder.boot.framework.iot.accessplatform.AccessManangerImpl;
import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformListProperties;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.GetPropertyEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.ServiceInvokeEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetConfigEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetPropertyEvent;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = AccessManangerTest.class)
@ActiveProfiles("unit-test")
@EnableConfigurationProperties({AccessPlatformListProperties.class})
@Import({
        AccessComponentFactory.class,
        EventListenerFactory.class,
})
public class AccessManangerTest {

    @Resource
    private AccessPlatformListProperties accessPlatformListProperties;

    @Resource
    private AccessComponentFactory accessComponentFactory;

    @Resource
    private EventListenerFactory eventListenerFactory;


    @Test
    public void testAccessInitAndStartup() throws InterruptedException {

        AccessMananger accessMananger = new AccessManangerImpl(this.accessComponentFactory,this.eventListenerFactory,this.accessPlatformListProperties);
        accessMananger.init(accessPlatformListProperties);
        accessMananger.start();


        Thread.sleep(10000);
    }

    @Test
    public void testSubDeviceOnlineAndOffLine() throws InterruptedException {
        AccessMananger accessMananger = new AccessManangerImpl(this.accessComponentFactory,this.eventListenerFactory,this.accessPlatformListProperties);
        accessMananger.init(accessPlatformListProperties);
        accessMananger.start();

        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "openiitapump01");
        extraProperties.put("device-name", "pump202308251717");
        String deviceId = "code202308231314";
        accessMananger.online(deviceId, "", extraProperties);

        Thread.sleep(10000);
        accessMananger.offline(deviceId);
        Thread.sleep(10000);
    }

    @Test
    public void testReportData() throws InterruptedException {
        AccessMananger accessMananger = new AccessManangerImpl(this.accessComponentFactory,this.eventListenerFactory,this.accessPlatformListProperties);
        accessMananger.init(accessPlatformListProperties);
        accessMananger.start();

        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "QHwYakiQZDJPH3JN");
        extraProperties.put("device-name", "202308211645");
        String deviceId = "code202308231314";
        accessMananger.online(deviceId, "", extraProperties);

        Thread.sleep(10000);

        Map<String, Object> data = new HashMap<>();
        data.put("speed", 888);
        data.put("high", 777);
        accessMananger.reportData(deviceId, new Date(), data);

        Thread.sleep(10000);

        accessMananger.offline(deviceId);

        Thread.sleep(10000);
    }

    @Test
    public void testEventListener() throws InterruptedException {
        AccessMananger accessMananger = new AccessManangerImpl(this.accessComponentFactory,this.eventListenerFactory,this.accessPlatformListProperties);
        accessMananger.init(accessPlatformListProperties);
        accessMananger.start();

        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "QHwYakiQZDJPH3JN");
        extraProperties.put("device-name", "202308211645");
        String deviceId = "code202308231314";
        accessMananger.online(deviceId, "", extraProperties);

        Thread.sleep(300000);

        accessMananger.offline(deviceId);

    }

    @Slf4j
    @Component
    public static class TestEnentListener implements EventListener {

        private AccessMananger accessMananger;

        public TestEnentListener(AccessMananger accessMananger) {
            this.accessMananger = accessMananger;
        }

        @Override
        public String getAccessPlatformName() {
            return "iita";
        }

        @Override
        public void onSetProperty(SetPropertyEvent event) {
            log.info("onSetProperty event=" + JsonUtils.toJsonString(event));
        }

        @Override
        public void onGetProperty(GetPropertyEvent event) {
            log.info("onGetProperty event=" + JsonUtils.toJsonString(event));
            List<String> propertyNames = event.getPropertyNames();
            HashMap<String, Object> data = new HashMap<>();
            propertyNames.stream().forEach(x -> {
                if (x.equals("speed"))
                    data.put("speed", 103);
                else if (x.equals("high"))
                    data.put("high", 993);
            });

            accessMananger.reportData("code202308231314", new Date(), data);
        }

        @Override
        public void onSetConfig(SetConfigEvent event) {
            log.info("onSetConfig event=" + JsonUtils.toJsonString(event));
        }

        @Override
        public void onServiceInvoke(ServiceInvokeEvent event) {
            log.info("onServiceInvoke event=" + JsonUtils.toJsonString(event));
        }
    }
}
