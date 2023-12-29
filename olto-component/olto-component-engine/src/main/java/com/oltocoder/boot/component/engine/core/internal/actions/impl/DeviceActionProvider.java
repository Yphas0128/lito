package com.oltocoder.boot.component.engine.core.internal.actions.impl;

import com.oltocoder.boot.component.engine.core.rule.RuleNodeModel;
import com.oltocoder.boot.component.engine.core.internal.actions.SceneActionProvider;
import com.oltocoder.boot.component.engine.core.task.provider.impl.DeviceMessageSendTaskExecutorProvider;
import com.oltocoder.boot.framework.common.enums.ActionTypeEnum;

import java.util.List;

/**
 * @title: 设备动作
 * @Author Ypier
 * @Date: 2023/9/2 14:20
 */
public class DeviceActionProvider implements SceneActionProvider<DeviceAction> {

    @Override
    public String getProvider() {
        return ActionTypeEnum.device.name();
    }

    @Override
    public void applyRuleNode(DeviceAction device, RuleNodeModel nodeModel) {

        DeviceMessageSendTaskExecutorProvider.DeviceMessageSendConfig config = new DeviceMessageSendTaskExecutorProvider.DeviceMessageSendConfig(device.getProductId(), device.getMessage());
        config.setSelectorValues(device.getSelectorValues());

        nodeModel.setExecutor(getProvider());
        nodeModel.setConfiguration(config.convertToMap());
    }

    @Override
    public List<String> parseColumns(DeviceAction config) {
        return config.parseColumns();
    }
}
