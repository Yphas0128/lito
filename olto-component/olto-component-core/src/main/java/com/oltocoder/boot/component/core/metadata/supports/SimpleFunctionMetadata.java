package com.oltocoder.boot.component.core.metadata.supports;

import com.oltocoder.boot.component.core.metadata.FunctionMetadata;
import com.oltocoder.boot.component.core.metadata.Jsonable;
import com.oltocoder.boot.component.core.metadata.PropertyMetadata;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SimpleFunctionMetadata implements FunctionMetadata {

    private transient JSONObject jsonObject;

    @Getter
    @Setter
    private String identifier; // 标识符
    @Getter
    @Setter
    private String name; // 名称

    @Getter
    private String description; // 描述

    @Setter
    private List<PropertyMetadata> inputs; // 输入参数

    @Setter
    private List<PropertyMetadata> outputs; // 输出参数

    public SimpleFunctionMetadata(JSONObject jsonObject) {
        fromJson(jsonObject);
    }

    @Override
    public List<PropertyMetadata> getInputs() {
        if (inputs == null && jsonObject != null) {
            inputs = Optional.ofNullable(jsonObject.getJSONArray("inputs"))
                    .map(Collection::stream)
                    .map(stream -> stream
                            .map(JSONObject.class::cast)
                            .map(SimplePropertyMetadata::new)
                            .map(PropertyMetadata.class::cast)
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        }
        return inputs;
    }

    @Override
    public List<PropertyMetadata> getOutputs() {
        if (outputs == null && jsonObject != null) {
            outputs = Optional.ofNullable(jsonObject.getJSONArray("outputs"))
                    .map(Collection::stream)
                    .map(stream -> stream
                            .map(JSONObject.class::cast)
                            .map(SimplePropertyMetadata::new)
                            .map(PropertyMetadata.class::cast)
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
        }
        return outputs;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("description", description);
        json.put("inputs", getInputs().stream().map(Jsonable::toJson).collect(Collectors.toList()));
        json.put("outputs", getOutputs().stream().map(Jsonable::toJson).collect(Collectors.toList()));

        return json;
    }

    @Override
    public void fromJson(JSONObject json) {
        this.jsonObject = json;
        this.inputs = null;
        this.outputs = null;
        this.identifier = json.getString("identifier");
        this.name = json.getString("name");
        this.description = json.getString("description");
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
