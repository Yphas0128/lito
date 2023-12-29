package com.oltocoder.boot.framework.iot.accessplatform.accesscomponents.iita;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public  class MqttTopicUtil {
    public static String doTopicFormat(String topic, Object... params) {
        StringBuilder sbuf = new StringBuilder(topic.length() + 50);
        int i = 0;
        int j;
        int l;
        for (l = 0; l < params.length; l++) {
            j = topic.indexOf("{}", i);
            if (j == -1) {
                log.error("[doTopicFormat][参数过多：主题({})|参数({})", topic, params);
                if (i == 0) {
                    return topic;
                } else {
                    sbuf.append(topic.substring(i));
                    return sbuf.toString();
                }
            } else {
                sbuf.append(topic, i, j);
                sbuf.append(params[l]);
                i = j + 2;
            }
        }
        if (topic.indexOf("{}", i) != -1) {
            log.error("[doTopicFormat][参数过少：主题({})|参数({})", topic, params);
        }
        sbuf.append(topic.substring(i));
        return sbuf.toString();
    }
}
