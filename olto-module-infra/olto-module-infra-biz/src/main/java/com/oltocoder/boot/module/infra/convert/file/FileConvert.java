package com.oltocoder.boot.module.infra.convert.file;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.infra.controller.admin.file.vo.file.FileRespVO;
import com.oltocoder.boot.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
