package com.oltocoder.boot.component.engine.core.task.provider.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.engine.core.entity.selector.SelectorValue;
import com.oltocoder.boot.component.engine.core.task.context.ExecutionContext;
import com.oltocoder.boot.component.engine.core.task.executor.FunctionTaskExecutor;
import com.oltocoder.boot.component.engine.core.task.executor.TaskExecutor;
import com.oltocoder.boot.component.engine.core.task.provider.TaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.util.RuleDataUtils;
import com.oltocoder.boot.module.iot.api.device.DeviceApi;
import com.oltocoder.boot.module.iot.api.device.dto.DeviceOperatorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DeviceMessageSendTaskExecutorProvider implements TaskExecutorProvider {

    public static final String EXECUTOR = "device-message-sender";

    private final DeviceApi deviceApi;

    @Override
    public String getExecutor() {
        return EXECUTOR;
    }

    @Override
    public Mono<TaskExecutor> createTask(ExecutionContext context) {
        return Mono.just(new DeviceMessageSendTaskExecutor(context));
    }

    class DeviceMessageSendTaskExecutor extends FunctionTaskExecutor {

        private DeviceMessageSendConfig config;


        public DeviceMessageSendTaskExecutor(ExecutionContext context) {
            super("发送设备消息", context);
            reload();
        }

        @Override
        public void reload() {

            config = BeanUtil.toBean(context.getJob().getConfiguration(), DeviceMessageSendConfig.class );
            if(config.getSelectorValues()!= null && CollUtil.isNotEmpty(config.getSelectorValues())) {
                if(CollUtil.isNotEmpty(config.getSelectorValues())){
                    Flux.fromIterable(config.getSelectorValues())
                            .mapNotNull(SelectorValue::getValue)
                            .map(String::valueOf)
                            .flatMap(deviceId -> {
                                DeviceOperatorDTO deviceOperator = deviceApi.getDeviceOperator(deviceId);
                                return Mono.just(deviceOperator);
                            });
                }
            }

        }

        @Override
        protected Publisher<RuleData> apply(RuleData input) {
            Map<String, Object> ctx = RuleDataUtils.toContextMap(input);


            return null;
        }
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

        public Map<String, Object> convertToMap() {
            return  BeanUtil.beanToMap(this, false, true);
        }

    }
}
