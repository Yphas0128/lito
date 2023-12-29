package com.oltocoder.boot.component.core.metadata;

import java.util.List;

public interface FunctionMetadata extends Metadata, Jsonable{

    // 输入参数
    List<PropertyMetadata> getInputs();

    // 输出参数
    List<PropertyMetadata> getOutputs();
}
