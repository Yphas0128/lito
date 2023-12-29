package com.oltocoder.boot.framework.iot.core.factory;

import com.oltocoder.boot.framework.iot.client.ClientSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultClientSystemFactory implements ClientSystemFactory {
    private Logger logger = LoggerFactory.getLogger(getClass());


    private Map<String, ClientSystem> clientSystemMap = new HashMap<>();
    private static DefaultClientSystemFactory clientSystemFactory = new DefaultClientSystemFactory();

    protected DefaultClientSystemFactory() { }

    public static ClientSystemFactory getInstance() {
        return clientSystemFactory;
    }
    @Override
    public ClientSystem getSystemFactory(String name) {
        return clientSystemMap.get(name);
    }

    @Override
    public synchronized void register(ClientSystem clientSystem) {
//        clientSystemMap.put(clientSystem.getName(), clientSystem);
    }
}
