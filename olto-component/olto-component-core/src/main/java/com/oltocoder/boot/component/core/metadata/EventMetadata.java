package com.oltocoder.boot.component.core.metadata;

import java.util.List;

public interface EventMetadata extends Metadata, Jsonable {

    List<PropertyMetadata> getOutputs();
}
