package com.oltocoder.boot.component.core.expression.comparator.impl;

import com.oltocoder.boot.component.core.expression.comparator.AbstractComparator;

public class EqComparator extends AbstractComparator {

    @Override
    public String getName() {
        return ">";
    }

    @Override
    public String getScript() {
        return "left>right";
    }
}
