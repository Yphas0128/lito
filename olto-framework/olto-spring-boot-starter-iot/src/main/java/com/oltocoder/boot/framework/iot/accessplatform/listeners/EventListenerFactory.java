package com.oltocoder.boot.framework.iot.accessplatform.listeners;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EventListenerFactory {

    private Map<String, EventListener> accessPlatformNameAndListenerMap;

    public EventListenerFactory(){
        this.accessPlatformNameAndListenerMap=new HashMap<>();
    }

    public void init(List<EventListener> eventListeners){
        if(eventListeners!=null) {
            this.accessPlatformNameAndListenerMap = eventListeners.stream().collect(Collectors.toMap(x -> x.getAccessPlatformName(), y -> y));
        }
    }

    public void register(String accessPlatformName, EventListener listener){
        this.accessPlatformNameAndListenerMap.put(accessPlatformName,listener);
    }

    public EventListener getListener(String accessPlatformName) {
        return this.accessPlatformNameAndListenerMap.get(accessPlatformName);
    }
}
