package com.oltocoder.boot.component.engine.core.task.executor.impl;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.engine.core.entity.selector.SelectorValue;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.FunctionTaskExecutor;
import com.oltocoder.boot.module.iot.api.device.DeviceApi;
import com.oltocoder.boot.module.iot.api.device.dto.DeviceOperatorDTO;
import lombok.Getter;
import lombok.Setter;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

public class DeviceMessageSendTaskExecutor extends FunctionTaskExecutor {
    private final DeviceMessageSendConfig config;

    private final DeviceApi deviceApi;

    public DeviceMessageSendTaskExecutor(ExecutionContext context,
                                          DeviceMessageSendConfig config,
                                         DeviceApi deviceApi) {
        super("发送设备消息", context);
        this.config = config;
        this.deviceApi = deviceApi;
        reload();
    }

    @Override
    public void reload() {
        if(config.getSelectorValues()!= null && CollUtil.isNotEmpty(config.getSelectorValues())) {
            Flux.fromIterable(config.getSelectorValues())
                    .mapNotNull(SelectorValue::getValue)
                    .map(String::valueOf)
                    .flatMap(deviceId -> {
                        DeviceOperatorDTO deviceOperator = deviceApi.getDeviceOperator(deviceId);
                        return Mono.just(deviceOperator);
                    });
        }

    }

    @Override
    protected Publisher<RuleData> apply(RuleData input) {
        return null;
    }

    @Getter
    @Setter
    public static class DeviceMessageSendConfig {

        private String productId; // 产品ID

        private String deviceId; // 设备ID

        private Map<String, Object> message;

        private List<SelectorValue> selectorValues;
        public DeviceMessageSendConfig(String productId,Map<String, Object> message){
            this.productId  = productId;
            this.message = message;
        }

        // 转化成Map
//        public Map<String, Object> toMap() {
//          return  BeanUtil.beanToMap(this, false, true);
//        }
    }
}
