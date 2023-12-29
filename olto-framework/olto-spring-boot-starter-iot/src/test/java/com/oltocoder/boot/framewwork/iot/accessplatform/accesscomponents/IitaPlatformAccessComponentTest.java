package com.oltocoder.boot.framewwork.iot.accessplatform.accesscomponents;


import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.accessplatform.AccessPlatformApplicationRunner;
import com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita.IitaPlatformAccessComponent;
import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformListProperties;
import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformProperties;
import com.oltocoder.boot.framework.iot.accessplatform.data.OnlineData;
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
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = IitaPlatformAccessComponentTest.class)
@ActiveProfiles("unit-test")
@Import({AccessPlatformApplicationRunner.class})
@EnableConfigurationProperties({AccessPlatformListProperties.class})
public class IitaPlatformAccessComponentTest {


    @Resource
    private AccessPlatformListProperties accessGatewayListProperties;

    @Resource
    private EventListenerFactory listenerFactory;

    @Test
    public void testConnect() throws InterruptedException {
        IitaPlatformAccessComponent iitaPlatformAccessComponent=new IitaPlatformAccessComponent();
        List<AccessPlatformProperties> property = this.accessGatewayListProperties.getAccessPlatforms().values().stream().collect(Collectors.toList());
        iitaPlatformAccessComponent.setProperties(property.get(0));
        iitaPlatformAccessComponent.setListenerFactory(listenerFactory);
        iitaPlatformAccessComponent.start();
        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "QHwYakiQZDJPH3JN");
        extraProperties.put("device-name", "202308211645");
        OnlineData data = OnlineData.builder().deviceId("code202308210639")
                .extraProperties(extraProperties)
                .build();
        iitaPlatformAccessComponent.online(data);

     /*   Map<String, Object> reportDataParams = new HashMap<>();
        reportDataParams.put("speed", 1111);
        reportDataParams.put("high",345);
        ReportData reportData = ReportData.builder()
                .deviceId("code202308210639")
                .collectTime(LocalDateTime.now())
                .data(reportDataParams).build();
        iitaPlatformAccessComponent.reportData(reportData);*/
    /*    Thread.sleep(10000);
        iitaPlatformAccessComponent.offlineAll();
        Thread.sleep(10000);
        iitaPlatformAccessComponent.disconnect();
        System.out.println("offlineAll ");*/
        Thread.sleep(600000);
    }

    @Slf4j
    public static class TestEnentListener implements EventListener {

        @Override
        public String getAccessPlatformName() {
            return null;
        }

        @Override
        public void onSetProperty(SetPropertyEvent event) {
            log.info("onSetProperty event=" + JsonUtils.toJsonString(event));
        }

        @Override
        public void onGetProperty(GetPropertyEvent event) {
            log.info("onGetProperty event=" + JsonUtils.toJsonString(event));
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
