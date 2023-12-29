package com.oltocoder.boot.component.core.metadata;

public interface DataType extends Metadata,FormatSupport {

    default String getType() {
        return getIdentifier();
    }
}
