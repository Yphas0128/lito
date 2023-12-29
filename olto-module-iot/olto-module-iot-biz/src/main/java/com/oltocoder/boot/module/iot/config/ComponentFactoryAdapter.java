package com.oltocoder.boot.module.iot.config;

import com.oltocoder.boot.module.iot.config.dto.DeviceDTO;
import com.iteaj.iot.client.ClientComponent;
import com.iteaj.iot.client.ClientComponentFactory;
import com.iteaj.iot.client.ClientConnectProperties;
import com.iteaj.iot.client.SocketClient;
import com.iteaj.iot.client.component.SocketClientComponent;
import org.springframework.stereotype.Component;

@Component
public class ComponentFactoryAdapter {

    private final ClientComponentFactory componentFactory;

    public ComponentFactoryAdapter(ClientComponentFactory componentFactory){
        this.componentFactory = componentFactory;
    }

    public ClientComponent getFrameworkComponent(String name) {

        for (ClientComponent component: componentFactory.getComponents()) {
            if(name.equals(component.getName()))
                return component;
        }
        return null;
    }


    public void createAndConnect(DeviceDTO deviceDTO) {

        ClientComponent clientComponent = getFrameworkComponent(deviceDTO.getComponentName());
        ClientConnectProperties properties = new ClientConnectProperties(deviceDTO.getHost(), deviceDTO.getPort(), deviceDTO.getCode());
        if (clientComponent instanceof SocketClientComponent) {
            SocketClient client = ((SocketClientComponent) clientComponent).getClient(properties);
            if (client == null) {
                ((SocketClientComponent) clientComponent).createNewClientAndConnect(properties);
            } else {
                client.reconnection();
            }
        }
    }
}
