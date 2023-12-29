package com.oltocoder.boot.module.iot.dal.mysql.scene;

import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.module.iot.dal.dataobject.scene.SceneDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SceneMapper extends BaseMapperX<SceneDO> {
}
