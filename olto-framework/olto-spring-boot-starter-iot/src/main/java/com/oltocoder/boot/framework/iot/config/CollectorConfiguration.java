package com.oltocoder.boot.framework.iot.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("olto.iot.collector")
@Data
public class CollectorConfiguration {

    private Integer count;
    private Integer interval;
}
