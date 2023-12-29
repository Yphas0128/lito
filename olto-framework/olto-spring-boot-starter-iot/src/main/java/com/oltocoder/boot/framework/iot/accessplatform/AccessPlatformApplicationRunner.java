package com.oltocoder.boot.framework.iot.accessplatform;

import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AccessPlatformApplicationRunner implements ApplicationRunner {

    @Autowired(required = false)
    private List<EventListener> eventListeners;

    @Resource
    private EventListenerFactory eventListenerFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        eventListenerFactory.init(eventListeners);
    }
}
