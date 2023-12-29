package com.oltocoder.boot.framework.iot.core.factory;

import com.oltocoder.boot.framework.iot.client.ClientSystem;

public interface ClientSystemFactory {

    ClientSystem getSystemFactory(String name);

    void register(ClientSystem clientSystem);


}
