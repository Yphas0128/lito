package com.oltocoder.boot.component.core.metadata.supports;

import com.oltocoder.boot.component.core.metadata.*;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

// 物模型
public class SimpleThingMetadata implements ThingMetadata {

    private JSONObject jsonObject;

    private volatile Map<String, PropertyMetadata> properties;

    private volatile Map<String, FunctionMetadata> functions;

    private volatile Map<String, EventMetadata> events;

    @Getter
    @Setter
    private String identifier; //标志

    @Getter
    @Setter
    private String name ; // 名称

    @Getter
    private String description; //
    public SimpleThingMetadata(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public SimpleThingMetadata(ThingMetadata metadata) {
    }


    @Override
    public List<PropertyMetadata> getProperties() {
        if (this.properties == null && jsonObject != null) {
            this.properties = Optional.ofNullable(jsonObject.getJSONArray("properties"))
                    .map(Collection::stream)
                    .<Map<String, PropertyMetadata>>map(stream -> stream
                            .map(JSONObject.class::cast)
                            .map(SimplePropertyMetadata::new)
                            .map(PropertyMetadata.class::cast)
                            .collect(Collectors.toMap(PropertyMetadata::getIdentifier, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                    ).orElse(Collections.emptyMap());
            return  Collections.unmodifiableList(new ArrayList<>(this.properties.values()));
        }

        return Collections.emptyList();
    }

    @Override
    public List<FunctionMetadata> getFunctions() {
        if (functions == null && jsonObject != null) {
            functions = Optional
                    .ofNullable(jsonObject.getJSONArray("functions"))
                    .map(Collection::stream)
                    .<Map<String, FunctionMetadata>>map(stream -> stream
                            .map(JSONObject.class::cast)
                            .map(SimpleFunctionMetadata::new)
                            .map(FunctionMetadata.class::cast)
                            .collect(Collectors.toMap(FunctionMetadata::getIdentifier, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                    )
                    .orElse(Collections.emptyMap());
        }
        if (functions == null) {
            this.functions = new LinkedHashMap<>();
        }
        return new ArrayList<>(functions.values());
    }

    @Override
    public List<EventMetadata> getEvents() {
        if (events == null && jsonObject != null) {
            events = Optional
                    .ofNullable(jsonObject.getJSONArray("events"))
                    .map(Collection::stream)
                    .<Map<String, EventMetadata>>map(stream -> stream
                            .map(JSONObject.class::cast)
                            .map(SimpleEventMetadata::new)
                            .map(EventMetadata.class::cast)
                            .collect(Collectors.toMap(EventMetadata::getIdentifier, Function.identity(), (a, b) -> a, LinkedHashMap::new))
                    )
                    .orElse(Collections.emptyMap());
        }
        if (events == null) {
            this.events = new LinkedHashMap<>();
        }
        return new ArrayList<>(events.values());
    }

    @Override
    public PropertyMetadata getPropertyOrNull(String id) {
        if (properties == null) {
            return null;
        }
        return properties.get(id);
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("description", description);
        json.put("properties", getProperties().stream().map(Jsonable::toJson).collect(Collectors.toList()));
        json.put("functions", getFunctions().stream().map(Jsonable::toJson).collect(Collectors.toList()));
        json.put("events", getEvents().stream().map(Jsonable::toJson).collect(Collectors.toList()));
        return json;
    }

    @Override
    public void fromJson(JSONObject json) {
        this.jsonObject = json;
        this.properties = null;
        this.events = null;
        this.functions = null;
        this.identifier = json.getString("identifier");
        this.name = json.getString("name");
        this.description = json.getString("description");
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
