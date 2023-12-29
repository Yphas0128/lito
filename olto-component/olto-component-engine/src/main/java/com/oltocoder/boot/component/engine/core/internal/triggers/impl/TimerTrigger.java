package com.oltocoder.boot.component.engine.core.internal.triggers.impl;

import com.oltocoder.boot.component.core.community.TimerSpec;
import com.oltocoder.boot.component.engine.core.internal.triggers.ITriggerConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerTrigger implements ITriggerConfig {

    //cron 表达式
    private TimerSpec timer;


    @Override
    public void validate() {

    }
}
