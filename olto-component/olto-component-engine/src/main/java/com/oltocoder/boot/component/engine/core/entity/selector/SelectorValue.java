package com.oltocoder.boot.component.engine.core.entity.selector;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class SelectorValue {

    /**
     * 值
     */
    private Object value;


    /**
     * 名称
     */
    private String name;
}
