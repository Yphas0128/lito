package com.oltocoder.boot.framework.iot.config;

import com.oltocoder.boot.framework.iot.concurrent.ContextInternal;
import com.oltocoder.boot.framework.iot.client.modbus.ModBusTcpClientSystem;
import com.oltocoder.boot.framework.iot.concurrent.WorkerContext;
import com.oltocoder.boot.framework.iot.concurrent.WorkerPool;
import com.iteaj.iot.client.mqtt.gateway.MqttGatewayComponent;
import com.iteaj.iot.client.mqtt.impl.DefaultMqttComponent;
import com.iteaj.iot.modbus.client.tcp.ModbusTcpClientComponent;
import com.iteaj.iot.plc.omron.OmronComponent;
import com.iteaj.iot.plc.siemens.SiemensS7Component;
import com.iteaj.iot.serial.SerialComponent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class YuDaoIotAutoConfiguration {

    /**
     * 启用modbus rtu 组件
     */
//    @Bean
//    public ModbusRtuForDtuServerComponent modbusRtuForDtuServerComponent() {
//        return new ModbusRtuForDtuServerComponent(new ConnectProperties(7058, 90, 0, 0));
//    }

    /**
     * 启用modbus tcp 组件
     */
//    @Bean
//    public ModbusTcpForDtuServerComponent modbusTcpForDtuServerComponent() {
//        return new ModbusTcpForDtuServerComponent(new ConnectProperties(7068, 90, 0, 0));
//    }

    /**
     * modbus tcp client组件
     */
    @Bean
    public ModbusTcpClientComponent modbusTcpClientComponent() {
        return new ModbusTcpClientComponent();
    }

    @Bean
    public WorkerPool workerPool() {
        return new WorkerPool(Executors.newFixedThreadPool(3), null);
    }

    @Bean
    public ContextInternal contextInternal(WorkerPool workerPool) {
        return new WorkerContext(workerPool);
    }

    @Bean
    public ModBusTcpClientSystem modBusTcpClientSystem(ContextInternal contextInternal) {
        return new ModBusTcpClientSystem(contextInternal);
    }

//    @Bean
//    public void registerClientSystemFactory(ObjectProvider<ClientSystem> clientSystems){
//
//        clientSystems.forEach(clientSystem -> {
//            DefaultClientSystemFactory.getInstance().register(clientSystem);
//        });
//    }
//
//    @Bean
//    public ModbusRtuClientCollectAction modbusRtuClientCollectAction(ISerialService serialService) {
//        return new ModbusRtuClientCollectAction(serialService);
//    }

    /**
     * 通用dtu设备组件
     */
//    @Bean
//    public CommonDtuServerComponent commonDtuServerComponent() {
//        return new CommonDtuServerComponent(new ConnectProperties(7078, 90, 0, 0));
//    }

    /**
     * 西门子S7 PLC组件
     */
    @Bean
    public SiemensS7Component siemensS7Component() {
        return new SiemensS7Component();
    }

    /**
     * 欧姆龙PLC组件
     */
    @Bean
    public OmronComponent omronComponent() {
        return new OmronComponent();
    }

//    @Bean
//    @ConditionalOnBean(OmronComponent.class)
//    public OmronCollectAction omronCollectAction() {
//        return new OmronCollectAction();
//    }
//
//    @Bean
//    @ConditionalOnBean(SiemensS7Component.class)
//    public SiemensCollectAction siemensCollectAction() {
//        return new SiemensCollectAction();
//    }

    /**
     * 启用串口组件
     */
    @Bean
    public SerialComponent serialComponent() {
        return SerialComponent.instance();
    }

//    @Bean
//    @ConditionalOnBean(ModbusRtuForDtuServerComponent.class)
//    public ModbusRtuForDTUCollectAction modbusRtuForDTUCollectAction() {
//        return new ModbusRtuForDTUCollectAction();
//    }
//
//    @Bean
//    @ConditionalOnBean(ModbusTcpForDtuServerComponent.class)
//    public ModbusTcpForDTUCollectAction modbusTcpForDTUCollectAction() {
//        return new ModbusTcpForDTUCollectAction();
//    }
//
//    @Bean
//    @ConditionalOnBean(CommonDtuServerComponent.class)
//    public CommonDtuCollectAction commonDtuCollectAction() {
//        return new CommonDtuCollectAction();
//    }
//
//    @Bean
//    public Pointctory collectActionFactory() {
//        return CollectActionFactory.getInstance();
//    }


    /**
     * mqtt
     */
    @Bean
    public MqttGatewayComponent mqttGatewayComponent() {
        return new MqttGatewayComponent();
    }

//    @Bean
//    public MqttStoreAction mqttStoreAction() {
//        return new MqttStoreAction();
//    }
//
//    @Bean
//    public StoreActionFactory storeActionFactory() {
//        return StoreActionFactory.getInstance();
//    }

    @Bean
    public DefaultMqttComponent defaultMqttComponent() {
        return new DefaultMqttComponent();
    }

}
