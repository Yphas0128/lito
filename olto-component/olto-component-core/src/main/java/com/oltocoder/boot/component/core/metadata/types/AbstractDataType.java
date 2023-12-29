package com.oltocoder.boot.component.core.metadata.types;

import com.oltocoder.boot.component.core.metadata.DataType;
import lombok.Getter;

public abstract class AbstractDataType<Self extends AbstractDataType<Self>> implements DataType {

    @Getter
    private String description;

    public Self description(String description) {
        this.description = description;
        return castSelf();
    }


    @SuppressWarnings("all")
    protected Self castSelf() {
        return (Self) this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
