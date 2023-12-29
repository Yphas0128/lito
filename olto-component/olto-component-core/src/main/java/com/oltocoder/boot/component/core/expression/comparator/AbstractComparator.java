package com.oltocoder.boot.component.core.expression.comparator;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractComparator implements IComparator {

    @Override
    public Map<String, Object> getData(Object left, Object right) {
        return new HashMap<String, Object>(){
            {
                put("left", left);
                put("right", right);
            }
        };
    }
}
