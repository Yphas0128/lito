package com.oltocoder.boot.module.infra.service.logger;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import com.oltocoder.boot.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExportReqVO;
import com.oltocoder.boot.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import com.oltocoder.boot.module.infra.convert.logger.ApiErrorLogConvert;
import com.oltocoder.boot.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import com.oltocoder.boot.module.infra.dal.mysql.logger.ApiErrorLogMapper;
import com.oltocoder.boot.module.infra.enums.logger.ApiErrorLogProcessStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.oltocoder.boot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.oltocoder.boot.module.infra.enums.ErrorCodeConstants.API_ERROR_LOG_NOT_FOUND;
import static com.oltocoder.boot.module.infra.enums.ErrorCodeConstants.API_ERROR_LOG_PROCESSED;

/**
 * API 错误日志 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Resource
    private ApiErrorLogMapper apiErrorLogMapper;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        ApiErrorLogDO apiErrorLog = ApiErrorLogConvert.INSTANCE.convert(createDTO)
                .setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        apiErrorLogMapper.insert(apiErrorLog);
    }

    @Override
    public PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO) {
        return apiErrorLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ApiErrorLogDO> getApiErrorLogList(ApiErrorLogExportReqVO exportReqVO) {
        return apiErrorLogMapper.selectList(exportReqVO);
    }

    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        ApiErrorLogDO errorLog = apiErrorLogMapper.selectById(id);
        if (errorLog == null) {
            throw exception(API_ERROR_LOG_NOT_FOUND);
        }
        if (!ApiErrorLogProcessStatusEnum.INIT.getStatus().equals(errorLog.getProcessStatus())) {
            throw exception(API_ERROR_LOG_PROCESSED);
        }
        // 标记处理
        apiErrorLogMapper.updateById(ApiErrorLogDO.builder().id(id).processStatus(processStatus)
                .processUserId(processUserId).processTime(LocalDateTime.now()).build());
    }

}
