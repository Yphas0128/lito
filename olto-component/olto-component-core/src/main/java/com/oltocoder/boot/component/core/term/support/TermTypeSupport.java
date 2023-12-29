package com.oltocoder.boot.component.core.term.support;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.term.TermType;

public interface TermTypeSupport {

    String getType();

    String getName();

    boolean isSupported(DataType type);

    default TermType type() {
        return TermType.of(getType(), getName());
    }
}
