package com.oltocoder.boot.framework.sms.core.client.impl.debug;

import com.oltocoder.boot.framework.common.exception.ErrorCode;
import com.oltocoder.boot.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.oltocoder.boot.framework.sms.core.client.SmsCodeMapping;
import com.oltocoder.boot.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author 芋道源码
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
