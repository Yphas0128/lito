package com.oltocoder.boot.framework.iot.accessplatform.config;

import com.oltocoder.boot.framework.iot.accessplatform.AccessComponentFactory;
import com.oltocoder.boot.framework.iot.accessplatform.AccessMananger;
import com.oltocoder.boot.framework.iot.accessplatform.config.AccessPlatformListProperties;
import com.oltocoder.boot.framework.iot.accessplatform.AccessManangerImpl;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListener;
import com.oltocoder.boot.framework.iot.accessplatform.listeners.EventListenerFactory;
import com.oltocoder.boot.framework.iot.config.YuDaoIotAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;


@AutoConfiguration
@AutoConfigureAfter(YuDaoIotAutoConfiguration.class)
@EnableConfigurationProperties(AccessPlatformListProperties.class)
public class YuDaoIotAccessPlatformAutoConfiguration {

}
