package com.oltocoder.boot.component.bus.core.manager;
import io.vertx.core.Vertx;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VertxManager {
    private static final Vertx INSTANCE = Vertx.vertx();

    public static Vertx getVertx() {
        return INSTANCE;
    }


    public final static List<String> mqList = new CopyOnWriteArrayList<>();
}
