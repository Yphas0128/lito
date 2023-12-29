package com.oltocoder.boot.component.engine.core.internal.triggers.impl;

import com.oltocoder.boot.component.core.community.TimerSpec;
import com.oltocoder.boot.component.engine.core.rule.RuleModel;
import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.entity.selector.SelectorValue;
import com.oltocoder.boot.component.engine.core.internal.triggers.ITriggerConfig;
import com.oltocoder.boot.component.engine.core.task.provider.impl.DeviceMessageSendTaskExecutorProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.TimerTaskExecutorProvider;
import com.oltocoder.boot.framework.common.enums.DeviceOperatorEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备触发
 */
@Getter
@Setter
public class DeviceTrigger implements ITriggerConfig {

    /**
     * 产品ID
     */
    private String productId;

    /**
     * 触发类型 => DeviceOperationEnum
     */
    private Integer operation;

    /**
     * [operator]为[readProperty,writeProperty,invokeFunction]时不能为空
     */
    private TimerSpec timer;

    /**
     * [operator]为[reportEvent]时不能为空
     */
    private String eventId;

    /**
     * [operator]为[readProperty]时不能为空
     */
    private List<String> readProperties;

    /**
     * [operator]为[writeProperty]时不能为空
     */
    private Map<String, Object> writeProperties;

    /**
     * [operator]为[invokeFunction]时不能为空
     */
    private String functionId;

    /**
     * [operator]为[invokeFunction]时不能为空
     */
    private List<Map<String,Object>> functionParameters;

    /**
     *选择器的值
     */
    private List<SelectorValue> selectorValues;

    public void applyModel(RuleModel model, RuleNodeModel sceneNode) {
        switch (DeviceOperatorEnum.valueOf(operation)){
            case online:
            case offline:
            case reportEvent:
            case reportProperty:
                return;
        }

        // 设备指令
        RuleNodeModel deviceNode = new RuleNodeModel();

        DeviceMessageSendTaskExecutorProvider.DeviceMessageSendConfig config = new DeviceMessageSendTaskExecutorProvider.DeviceMessageSendConfig(productId, toMessageTemplate());
        config.setSelectorValues(getSelectorValues());
        deviceNode.setId("scene:device:message");
        deviceNode.setExecutor(DeviceMessageSendTaskExecutorProvider.EXECUTOR);
        deviceNode.setConfiguration(config.convertToMap());

        model.getNodes().add(deviceNode);

        // 定时
        RuleNodeModel timerNode = new RuleNodeModel();

        timerNode.setId("scene:device:timer");
        timerNode.setExecutor(TimerTaskExecutorProvider.EXECUTOR);
        timerNode.setConfiguration(timer.convertToMap());

        model.getNodes().add(timerNode);

        // 定时->设备指令->场景
        model.link(timerNode, deviceNode);
        model.link(deviceNode, sceneNode);
    }


    // 属性赋值
    private Map<String, Object> toMessageTemplate() {
        Map<String, Object> map = new HashMap<>();
        switch (DeviceOperatorEnum.valueOf(operation)){
            case readProperty: // 读取属性
                map.put("properties", readProperties);
                break;
            case writeProperty: // 修改属性
                map.put("properties", writeProperties);
                break;
            case invokeFunction: //调用功能
                map.put("functionId", functionId);
                map.put("inputs",functionParameters );
                break;
        }

        return map;
    }

    @Override
    public void validate() {


    }
}
