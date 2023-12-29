package com.oltocoder.boot.component.bus.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public  @interface Subscribe {

    String topic();
}
