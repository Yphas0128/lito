package com.oltocoder.boot.module.iot.service.accessplatform;

import com.oltocoder.boot.framework.common.util.json.JsonUtils;
import com.oltocoder.boot.framework.iot.accessplatform.AccessComponentFactory;
import com.oltocoder.boot.framework.iot.accessplatform.AccessManangerImpl;
import com.oltocoder.boot.framework.iot.accessplatform.AccessPlatformApplicationRunner;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.GetPropertyEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.ServiceInvokeEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetConfigEvent;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.events.SetPropertyEvent;
import com.oltocoder.boot.framework.iot.accessplatform.config.YuDaoIotAccessPlatformAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = AccessServiceTest.class)
@ActiveProfiles("unit-test")
@Import({
        AccessPlatformApplicationRunner.class,
        YuDaoIotAccessPlatformAutoConfiguration.class,
        AccessManangerImpl.class,
        AccessComponentFactory.class,
        EventListenerFactory.class,
        AccessServiceImpl.class,
        AccessServiceTest.TestEnentListener2.class,
})
public class AccessServiceTest {

    @Resource
    private AccessManangerImpl accessMananger;

    @Resource
    private EventListenerFactory listenerFactory;

    @Resource
    private AccessService accessService1;


    @Test
    public void testOnlineAndOffline() throws InterruptedException {
        AccessServiceImpl accessService=new AccessServiceImpl(accessMananger);
        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "QHwYakiQZDJPH3JN"); // 设备名称
        extraProperties.put("device-name", "202308211645"); // id 主键
        String deviceId = "code202308231314";  //设备code
        accessService.online(null, deviceId, extraProperties);

        Thread.sleep(10000);

        accessService.offline(deviceId);

        Thread.sleep(10000);
    }

    @Test
    public void testReportData() throws InterruptedException {
        AccessServiceImpl accessService=new AccessServiceImpl(accessMananger);
        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "QHwYakiQZDJPH3JN");
        extraProperties.put("device-name", "202308211645");
        String deviceId = "code202308231314";
        accessService.online(null, deviceId, extraProperties);

        Thread.sleep(10000);

        Map<String, Object> data = new HashMap<>();
        data.put("speed", 11);
        data.put("high", 22);
        accessService.reportData(deviceId, new Date(), data);

        Thread.sleep(10000);

        accessService.offline(deviceId);

        Thread.sleep(10000);
    }

    @Test
    public void testEventListener() throws InterruptedException {
        AccessServiceImpl accessService=new AccessServiceImpl(accessMananger);

        TestEnentListener listener=new TestEnentListener(accessService);
        listenerFactory.register(listener.getAccessPlatformName(),listener);

        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "openiitapump01");
        extraProperties.put("device-name", "pump202308251717");
        String deviceId = "code202308231314";
        accessService.online(null, deviceId, extraProperties);

        Thread.sleep(300000);

        accessService.offline(deviceId);
    }


    @Test
    public void testEventListener2() throws InterruptedException {
        Map<String, String> extraProperties = new HashMap<>();
        extraProperties.put("product-key", "openiitapump01");
        extraProperties.put("device-name", "pump202308251717");
        String deviceId = "code202308231314";
        accessService1.online(null, deviceId, extraProperties);

        Thread.sleep(300000);

        accessService1.offline(deviceId);
    }



    @Slf4j
    public static class TestEnentListener implements EventListener {


        private AccessService accessService;


        public TestEnentListener(AccessService accessService){
            this.accessService=accessService;
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
                    data.put("speed", new Random().nextInt(100));
                else if (x.equals("high"))
                    data.put("high", new Random().nextInt(100) + 100);
            });

            accessService.reportData(event.getDeviceId(), new Date(), data);
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

    @Slf4j
    @Component
    public static class TestEnentListener2 implements EventListener {


        @Resource
        private AccessService accessService;

        @PostConstruct
        public  void test(){
            int i=0;
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
                    data.put("speed", new Random().nextInt(100));
                else if (x.equals("high"))
                    data.put("high", new Random().nextInt(100) + 100);
            });

            accessService.reportData(event.getDeviceId(), new Date(), data);
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
