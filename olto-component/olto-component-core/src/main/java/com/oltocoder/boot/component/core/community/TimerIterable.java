package com.oltocoder.boot.component.core.community;

import java.time.ZonedDateTime;
import java.util.Iterator;

public interface TimerIterable {

    Iterator<ZonedDateTime> iterator(ZonedDateTime baseTime);
}
