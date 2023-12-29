package com.oltocoder.boot.module.iot.service.collector;


import cn.hutool.core.util.ObjUtil;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorUpdateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointPageReqVO;
import com.oltocoder.boot.module.iot.convert.collector.CollectorConvert;
import com.oltocoder.boot.module.iot.convert.collector.CollectorPointConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorDO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorPointDO;
import com.oltocoder.boot.module.iot.dal.mysql.collector.CollectorMapper;
import com.oltocoder.boot.module.iot.dal.mysql.collector.CollectorPointMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.oltocoder.boot.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.oltocoder.boot.module.system.enums.ErrorCodeConstants.PROTOCOL_IS_BIND;

@Service("collectorService")
public class CollectorServiceImpl implements CollectorService {

    @Resource
    private CollectorMapper collectorMapper;

    @Resource
    private CollectorPointMapper collectorPointMapper;

    @Override
    public Long createCollector(CollectorCreateReqVO reqVO) {

        CollectorDO collectorDO = CollectorConvert.INSTANCE.convert01(reqVO);
        int count = collectorMapper.insert(collectorDO);
        return count>0?collectorDO.getId():0L;
    }


    @Override
    public int updateCollector(CollectorUpdateReqVO reqVO) {
        CollectorDO collectorDO = CollectorConvert.INSTANCE.convert02(reqVO);
        return collectorMapper.updateById(collectorDO);
    }


    @Override
    public int deleteCollector(Long id) {
        return collectorMapper.deleteById(id);
    }

    @Override
    public CollectorDO getCollector(Long id) {
        return collectorMapper.selectById(id);
    }

    @Override
    public PageResult<CollectorDO> getCollectorPage(CollectorPageReqVO reqVO) {
        return collectorMapper.pageResult(reqVO);
    }

    @Override
    public List<CollectorDO> getCollectorList() {
        return collectorMapper.selectList();
    }

    @Override
    public Long createCollectorPoint(CollectorPointCreateReqVO reqVO) {

        CollectorPointDO pointDO = getCollectorPoint(reqVO.getPointId());
        if(ObjUtil.isNotNull(pointDO))
            throw exception(PROTOCOL_IS_BIND);

        CollectorPointDO collectorPointDO =  CollectorPointConvert.INSTANCE.convert(reqVO);
        int count  = collectorPointMapper.insert(collectorPointDO);
        return count>0? collectorPointDO.getId():0L;
    }

    @Override
    public CollectorPointDO getCollectorPoint(Long pointId) {
        return collectorPointMapper.getCollectorPoint(pointId);
    }

    @Override
    public int deleteCollectorPoint(Long id) {
        return collectorPointMapper.deleteById(id);
    }

    @Override
    public PageResult<CollectorPointDO> getCollectorPointPage(CollectorPointPageReqVO reqVO) {
        return collectorPointMapper.getCollectorPointPage(reqVO);
    }

    @Override
    public List<CollectorPointDO> getPointsByFrequency(Integer frequency,List<Long> collectorIds) {
        return collectorPointMapper.getPointsByFrequency(frequency,collectorIds);
    }

    @Override
    public  List<CollectorPointDO> getPointByCollectorId(Long collectorId) {
        return collectorPointMapper.getPointByCollectorId(collectorId);
    }

    @Override
    public int updateCollectorState(Long id, Integer status) {
        return collectorMapper.updateCollectorState(id,status);
    }

    @Override
    public List<CollectorDO> getCollectorByRunningState(int state) {
        return collectorMapper.getCollectorByRunningState(state);
    }

    @Override
    public List<CollectorPointDO> getPointsByCollectorIds(List<Long> collectorIds) {
        return collectorPointMapper.getPointsByCollectorIds(collectorIds);
    }


}
