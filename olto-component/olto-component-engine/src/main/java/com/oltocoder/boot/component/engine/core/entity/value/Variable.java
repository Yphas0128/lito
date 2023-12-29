package com.oltocoder.boot.component.engine.core.entity.value;

import com.oltocoder.boot.component.core.metadata.DataType;
import com.oltocoder.boot.component.core.term.TermType;
import com.oltocoder.boot.component.core.term.TermTypeSupportFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Variable {

    public static final String OPTION_PRODUCT_ID = "productId";
    // 变量ID
    private String id;
    // 变量名
    private String name;
    // 列
    private String column;
    // 其他配置
    private Map<String, Object> options;

    // 类型
    private String type;

    //支持的条件类型
    private List<TermType> termTypes;

    public synchronized Map<String, Object> safeOptions() {
        return options == null ? options = new HashMap<>() : options;
    }

    public Variable withType(String type) {
        this.type = type;
        return this;
    }

    public Variable withType(DataType type) {
        withType(type.getIdentifier())
                .withTermType(TermTypeSupportFactory.lookup(type));
        return this;
    }

    public Variable withTermType(List<TermType> termTypes) {
        this.termTypes = termTypes;
        return this;
    }

    private Variable(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public Variable withOption(String key, Object value) {
        safeOptions().put(key, value);
        return this;
    }
    public static Variable of(String id, String name) {
        return new Variable(id, name);
    }
}
