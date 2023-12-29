package com.oltocoder.boot.component.core.metadata.supports;

import com.oltocoder.boot.component.core.metadata.EventMetadata;
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

public class SimpleEventMetadata implements EventMetadata {

    private JSONObject jsonObject;

    @Getter
    @Setter
    private String name; // 名称
    @Getter
    @Setter
    private String identifier; // 标识符
    @Getter
    private String description;
    private List<PropertyMetadata> outputs; // 输出参数


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


    public SimpleEventMetadata(JSONObject jsonObject) {
        fromJson(jsonObject);
    }
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("identifier", identifier);
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("outputs", getOutputs().stream().map(Jsonable::toJson).collect(Collectors.toList()));
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject json) {
        this.jsonObject = json;
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
