package com.oltocoder.boot.component.core.metadata;

import com.alibaba.fastjson.JSONObject;

public interface Jsonable {

     JSONObject toJson();

    void fromJson(JSONObject json);
}
