package com.oltocoder.boot.framework.iot.client;

import com.iteaj.iot.client.ClientComponent;
import com.iteaj.iot.client.ClientMessage;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: ClientComponentFactory
 * @Author cmw
 * @Date: 2023/8/9 11:04
 * @describe
 */
public class ClientComponentFactory implements InitializingBean {
    @Autowired(required = false)
    private List<ClientComponent> clientComponents;

    private Map<String, ClientComponent> clientComponentMap = new ConcurrentHashMap<>(8);
    private static ClientComponentFactory clientComponentFactory = new ClientComponentFactory();

    public static ClientComponentFactory getInstance() {
        return clientComponentFactory;
    }
    public List<ClientComponent> getComponents() {
        return this.clientComponents;
    }

    public ClientComponent get(String componentName) {
        if(!StringUtils.hasText(componentName)) {
            return null;
        }

        return clientComponentMap.get(componentName);
    }

    public boolean isExists(String componentName) {
        return clientComponentMap.containsKey(componentName);
    }

    public ClientComponent register(String actionName, ClientComponent clientComponent) {
        return clientComponentMap.putIfAbsent(actionName, clientComponent);
    }

    public ClientComponent register(ClientComponent clientComponent) {
        return clientComponentMap.putIfAbsent(clientComponent.getName(), clientComponent);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!CollectionUtils.isEmpty(clientComponents)) {
            this.clientComponents.forEach(item -> {
                if(!this.isExists(item.getName())) {
                    this.register(item);
                } else {
                    throw new BeanInitializationException("存在相同的Components["+item.getName()+"]");
                }
            });
        }
    }
}
