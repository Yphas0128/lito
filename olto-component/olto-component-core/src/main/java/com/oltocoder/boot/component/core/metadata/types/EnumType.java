package com.oltocoder.boot.component.core.metadata.types;

import com.oltocoder.boot.component.core.metadata.DataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class EnumType  extends AbstractDataType<EnumType> implements DataType {

    public static final String Identifier = "enum";

    private volatile List<Element> elements;

    @Override
    public String getIdentifier() {
        return Identifier;
    }

    @Override
    public String getName() {
        return "枚举";
    }

    @Override
    public Object format(Object value) {
        return null;
    }


    @Getter
    @Setter
    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    public static class Element {
        private String value;

        private String text;

        private String description;

        public static Element of(String value, String text) {
            return of(value, text, null);
        }

        public static Element of(Map<String, String> map) {
            return Element.of(map.get("value"), map.get("text"), map.get("description"));
        }

        public Map<String, Object> toMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("value", value);
            map.put("text", text);
            map.put("description", description);

            return map;
        }
    }
}
