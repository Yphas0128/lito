package com.oltocoder.boot.component.engine.core.util;

import com.google.common.collect.Maps;
import com.oltocoder.boot.component.bus.core.entity.RuleData;
import com.oltocoder.boot.component.core.consts.RuleDataConstants;

import java.util.Map;

public class RuleDataUtils {

    public static Map<String, Object> toContextMap(RuleData ruleData) {
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(32);

        ruleData.acceptMap(_map -> map.putAll(_map));
        if (map.isEmpty()) {
            map.put("data", ruleData.getData());
        }

        if(ruleData.getHeaders() != null){
            //填充上游数据
            for (Map.Entry<String, Object> entry : ruleData.getHeaders().entrySet()) {
                String key = entry.getKey();
                if (key.startsWith(RuleDataConstants.RECORD_DATA_TO_HEADER_KEY_PREFIX)) {
                    map.put(key.substring(RuleDataConstants.RECORD_DATA_TO_HEADER_KEY_PREFIX.length()),
                            entry.getValue());
                }
            }
        }
        return map;
    }
}
