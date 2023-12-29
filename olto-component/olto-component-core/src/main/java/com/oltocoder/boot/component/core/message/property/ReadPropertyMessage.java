package com.oltocoder.boot.component.core.message.property;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 读取属性
@Getter
@Setter
public class ReadPropertyMessage {

    /**
     * 要读取的属性列表,协议包可根据实际情况处理此参数,
     * 有的设备可能不支持读取指定的属性,则直接读取全部属性返回即可
     */
    private List<String> properties = new ArrayList<>();

    public ReadPropertyMessage addProperties(List<String> properties) {
        this.properties.addAll(properties);
        return this;
    }

    public ReadPropertyMessage addProperties(String... properties) {
        return addProperties(Arrays.asList(properties));
    }
}
