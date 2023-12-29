package com.oltocoder.boot.component.bus.core.entity;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.oltocoder.boot.component.core.util.radomId.RandomIdUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 规则数据 用于规则之间传递数据
 */
@Getter
@Setter
public class RuleData {
    /**
     * 数据ID
     */
    private String id;

    /**
     * 上下文ID
     */
    private String contextId;

    /**
     * 真实数据
     */
    private Object data;

    /**
     * 规则头
     * @param data
     * @return
     */
    private Map<String, Object> headers = new ConcurrentHashMap<>(16);


    public static RuleData create(Map<String,Object> data) {
        RuleData ruleData = new RuleData();
        ruleData.setId(RandomIdUtils.generate());
        ruleData.setContextId(RandomIdUtils.generate());
        ruleData.setData(data);
        return ruleData;
    }


    public RuleData newData(RuleData data) {
        RuleData ruleData = new RuleData();
        ruleData.id = RandomIdUtils.generate();
        ruleData.headers.putAll(headers);
        ruleData.data = data.getData();
        ruleData.contextId = contextId;
        //todo  RuleDataHelper.clearError(ruleData);
        return ruleData;
    }


    public RuleData newData(Map<String,Object> data) {

        RuleData ruleData = new RuleData();
        ruleData.id = RandomIdUtils.generate();
        ruleData.headers.putAll(headers);
        ruleData.data = data;
        ruleData.contextId = contextId;
//        RuleDataHelper.clearError(ruleData);
        return ruleData;
    }

    public void setHeader(String key, Object value) {
        headers.put(key, value);
    }


    /**
     *
     * @param consumer
     */
    public void acceptMap(Consumer<Map<String, Object>> consumer) {
        Object data = this.data;
        if (data == null) {
            return;
        } else if (data instanceof byte[]) {
            byte[] bytes = ((byte[]) data);
            if (bytes.length > 2) {
                if (/* { }*/(bytes[0] == 123 && bytes[bytes.length - 1] == 125)
                        || /* [ ] */(bytes[0] == 91 && bytes[bytes.length - 1] == 93)
                ) {
                    data = JSON.parse(bytes);
                }
            }
        } else if (data instanceof String) {
            String stringData = (String) data;
            if (stringData.startsWith("{") || stringData.startsWith("[")) {
                data = JSON.parse(stringData);
            }
        }

        if (data instanceof Map) {
            doAcceptMap(data, consumer);
        } else if (data instanceof RuleData) {
            ((RuleData) data).acceptMap(consumer);
        } else if (data instanceof Iterable) {
            ((Iterable) data).forEach(d -> doAcceptMap(d, consumer));
        } else {
            doAcceptMap(data, consumer);
        }
    }

    private void doAcceptMap(Object data, Consumer<Map<String, Object>> consumer) {
        if (data == null) {
            return;
        }
        if (data instanceof Map) {
            consumer.accept(((Map) data));
        } else {
            consumer.accept(BeanUtil.beanToMap(data));
        }
    }


//    public Flux<Map<String, Object>> dataToMap(){
//        return Flux.create(sink -> {
//            acceptMap(sink::next);
//            sink.complete();
//        });
//    }
//
//    private void acceptMap(Consumer<Map<String, Object>> consumer) {
//        Object data = this.data;
//        if(data == null){
//            return;
//        } else if (data instanceof byte[]) {
//            byte[] bytes = ((byte[]) data);
//            if(bytes.length > 2){
//                if (/*{}*/(bytes[0] == 123 && bytes[bytes.length - 1] == 125)
//                        ||/* [ ] */(bytes[0] == 91 && bytes[bytes.length - 1] == 93)) {
//                    data = JSON.parse(bytes);
//                }
//            } else if (data instanceof String) {
//                String stringData = (String) data;
//                if (stringData.startsWith("{") || stringData.startsWith("[")) {
//                    data = JSON.parse(stringData);
//                }
//            }
//
//            if (data instanceof Map) {
//                doAcceptMap(data, consumer);
//            } else if (data instanceof RuleData) {
//                ((RuleData) data).acceptMap(consumer);
//            } else if (data instanceof Iterable) {
//                ((Iterable) data).forEach(d -> doAcceptMap(d, consumer));
//            } else {
//                doAcceptMap(data, consumer);
//            }
//
//        }
//    }
//
//    private void doAcceptMap(Object data, Consumer<Map<String, Object>> consumer) {
//
//        if (data == null) {
//            return;
//        }
//        if (data instanceof Map) {
//            consumer.accept(((Map) data));
//        } else if (data instanceof Jsonable) {
//            consumer.accept(((Jsonable) data).toJson());
//        } else {
//            consumer.accept(FastBeanCopier.copy(data, HashMap::new));
//        }
//    }
}
