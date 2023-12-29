package com.oltocoder.boot.component.core.metadata.codec.datatype.impl;

import com.oltocoder.boot.component.core.metadata.types.EnumType;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Getter
@Setter
public class EnumDataTypeCodec extends AbstractDataTypeCodec<EnumType> {
    @Override
    public String getTypeId() {
        return EnumType.Identifier;
    }


    @Override
    public EnumType decode(EnumType type, Map<String, Object> config) {
        super.decode(type,config);
        JSONObject jsonObject = new JSONObject(config);

        ofNullable(jsonObject.getJSONArray("elements"))
                .map(list -> list.stream()
                        .map(JSONObject.class::cast)
                        .map(e -> EnumType.Element.of(e.getString("value"), e.getString("text"),e.getString("description")))
                        .collect(Collectors.toList()))
                .ifPresent(type::setElements);

        return type;
    }

    @Override
    protected void doEncode(Map<String, Object> encoded, EnumType type) {
        if(type.getElements()==null){
            return;
        }
        encoded.put("elements", type.getElements()
                .stream()
                .map(EnumType.Element::toMap).collect(Collectors.toList()));
    }
}
