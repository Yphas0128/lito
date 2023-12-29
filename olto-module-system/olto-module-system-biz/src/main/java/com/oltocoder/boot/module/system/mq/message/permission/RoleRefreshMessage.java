package com.oltocoder.boot.module.system.mq.message.permission;

import com.oltocoder.boot.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色数据刷新 Message
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.role.refresh";
    }

}
