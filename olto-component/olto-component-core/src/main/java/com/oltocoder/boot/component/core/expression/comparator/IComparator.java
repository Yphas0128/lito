package com.oltocoder.boot.component.core.expression.comparator;

import java.util.Map;

// 数据比较
public interface IComparator {

    String getName();

    String getScript();

    Map<String, Object> getData(Object left, Object right);
}
