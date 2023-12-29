package com.oltocoder.boot.framewwork.iot;

import com.oltocoder.boot.framework.iot.client.modbus.ModBusTcpClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.*;
import com.oltocoder.boot.framework.iot.dal.ModBusTcpBaseEntity;
import com.oltocoder.boot.framework.iot.concurrent.AsyncCallback;
import com.oltocoder.boot.framework.iot.concurrent.AsyncFuture;
import com.oltocoder.boot.framework.test.core.ut.BaseDbUnitTest;
import com.iteaj.iot.IotCoreConfiguration;
import com.iteaj.iot.client.IotClientBootstrap;
import com.iteaj.iot.modbus.client.tcp.ModbusTcpClientComponent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @title: ModBusTest
 * @Author cmw
 * @Date: 2023/8/10 14:54
 * @describe
 */

@Import({ModbusTcpClientComponent.class, IotCoreConfiguration.class, IotClientBootstrap.class})
public class ModBusTest extends BaseDbUnitTest {


    @Test
    public void test1() {
        try {
            ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

            WorkerPool workerPool = new WorkerPool(fixedThreadPool, null);
            ContextInternal ctx=new WorkerContext(workerPool);
            ModBusTcpClientSystem modBusTcpClientSystem=new ModBusTcpClientSystem(ctx);
            ModBusTcpBaseEntity modBusTcpBaseEntity=new ModBusTcpBaseEntity();
            modBusTcpBaseEntity.setStartAddress(1)
                    .setCmd("03")
                    .setFieldType(2)
                    .setIp("127.0.0.1")
                    .setPort(503)
                    .setDeviceSn("123");
            System.out.println(Thread.currentThread().getName());
            AsyncFuture<String> asyncFuture=modBusTcpClientSystem.performRead(modBusTcpBaseEntity);
            asyncFuture.onComplete(new AsyncCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    System.out.println(result);
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace(); // 输出异常信息到控制台

        }

    }



    @SpringBootApplication
    public static class Application {

    }
}
