package com.oltocoder.boot.module.iot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CollectorRunningStateEnum {


    /**
     * 未运行
     */
    STOP,
    /**
     * 暂停
     */
    RUNNING

}
