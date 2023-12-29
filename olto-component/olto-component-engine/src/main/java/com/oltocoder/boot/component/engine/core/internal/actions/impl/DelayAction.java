package com.oltocoder.boot.component.engine.core.internal.actions.impl;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.temporal.ChronoUnit;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DelayAction {

    /**
     *延迟时间
     */
    private int time;

    /**
     * 时间
     */
    private DelayUnit unit;

    @Getter
    @AllArgsConstructor
    public enum DelayUnit {
        seconds(ChronoUnit.SECONDS),
        minutes(ChronoUnit.MINUTES),
        hours(ChronoUnit.HOURS);
        final ChronoUnit chronoUnit;
    }
}
