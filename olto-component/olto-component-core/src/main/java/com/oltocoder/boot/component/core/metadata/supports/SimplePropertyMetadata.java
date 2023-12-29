package com.oltocoder.boot.component.core.metadata.supports;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.metadata.DataTypeFactory;
import com.oltocoder.boot.component.core.metadata.PropertyMetadata;
import com.oltocoder.boot.component.core.metadata.codec.datatype.DataTypeCodec;
import com.oltocoder.boot.component.core.metadata.codec.datatype.DataTypeCodecsFactory;
import com.oltocoder.boot.component.core.metadata.types.UnknownType;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;


public class SimplePropertyMetadata implements PropertyMetadata {

    @Getter
    @Setter
    private String identifier;

    @Getter
    @Setter
    private String name;
    // 描述
    @Getter
    private String description;

    @Setter
    private DataType dataType;

    private JSONObject json;

    public SimplePropertyMetadata() {

    }

    public SimplePropertyMetadata(JSONObject json) {
        fromJson(json);
    }


    @Override
    public DataType getValueType() {
        if (dataType == null && json != null) {
            dataType = parseDataType();
        }
        return dataType;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", identifier);
        json.put("name", name);
        json.put("description", description);
        json.put("valueType", DataTypeCodecsFactory.encode(getValueType()).orElse(null));
        return json;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        Objects.requireNonNull(jsonObject);
        this.json = jsonObject;
        this.identifier = json.getString("id");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.dataType = null;
    }

    protected Optional<DataTypeCodec<DataType>> getDataTypeCodec(DataType dataType) {
        String identifier = dataType.getIdentifier();
        return DataTypeCodecsFactory.getCodec(identifier);
    }

    private DataType parseDataType() {
        JSONObject dataTypeJson = json.getJSONObject("valueType");

        DataType dataType = Optional
                .ofNullable(dataTypeJson.getString("type"))
                .map(DataTypeFactory::lookup)
                .map(Supplier::get)
                .orElseGet(UnknownType::new);

        getDataTypeCodec(dataType)
                .ifPresent(codec -> codec.decode(dataType, dataTypeJson));

        return dataType;
    }
}
