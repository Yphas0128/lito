package com.oltocoder.boot.module.infra.dal.mysql.file;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.infra.controller.admin.file.vo.config.FileConfigPageReqVO;
import com.oltocoder.boot.module.infra.dal.dataobject.file.FileConfigDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileConfigMapper extends BaseMapperX<FileConfigDO> {

    default PageResult<FileConfigDO> selectPage(FileConfigPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FileConfigDO>()
                .likeIfPresent(FileConfigDO::getName, reqVO.getName())
                .eqIfPresent(FileConfigDO::getStorage, reqVO.getStorage())
                .betweenIfPresent(FileConfigDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FileConfigDO::getId));
    }

}
