package com.oltocoder.boot.module.system.dal.mysql.notify;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.mybatis.core.mapper.BaseMapperX;
import com.oltocoder.boot.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.oltocoder.boot.module.system.controller.admin.notify.vo.template.NotifyTemplatePageReqVO;
import com.oltocoder.boot.module.system.dal.dataobject.notify.NotifyTemplateDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotifyTemplateMapper extends BaseMapperX<NotifyTemplateDO> {

    default NotifyTemplateDO selectByCode(String code) {
        return selectOne(NotifyTemplateDO::getCode, code);
    }

    default PageResult<NotifyTemplateDO> selectPage(NotifyTemplatePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<NotifyTemplateDO>()
                .likeIfPresent(NotifyTemplateDO::getCode, reqVO.getCode())
                .likeIfPresent(NotifyTemplateDO::getName, reqVO.getName())
                .eqIfPresent(NotifyTemplateDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(NotifyTemplateDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(NotifyTemplateDO::getId));
    }

}
