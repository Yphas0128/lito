package com.oltocoder.boot.component.core.metadata;

import java.util.List;

public interface ThingMetadata extends Metadata,Jsonable{

    /**
     * @return 属性定义
     */
    List<PropertyMetadata> getProperties();

    /**
     * @return 功能定义
     */
    List<FunctionMetadata> getFunctions();

    /**
     * @return 事件定义
     */
    List<EventMetadata> getEvents();

    /**
     *
     * @param id
     * @return
     */
    PropertyMetadata getPropertyOrNull(String id);
}
