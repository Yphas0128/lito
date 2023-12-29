package com.oltocoder.boot.module.iot.service.collector;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.CollectorUpdateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.collector.vo.point.CollectorPointPageReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorDO;
import com.oltocoder.boot.module.iot.dal.dataobject.collector.CollectorPointDO;

import java.util.List;

public interface CollectorService {
    Long createCollector(CollectorCreateReqVO reqVO);

    int updateCollector(CollectorUpdateReqVO reqVO);

    int deleteCollector(Long id);

    CollectorDO getCollector(Long id);

    PageResult<CollectorDO> getCollectorPage(CollectorPageReqVO reqVO);

    List<CollectorDO> getCollectorList();


    /// point
    Long createCollectorPoint(CollectorPointCreateReqVO reqVO);

    CollectorPointDO getCollectorPoint(Long pointId);

    int deleteCollectorPoint(Long id);

    PageResult<CollectorPointDO> getCollectorPointPage(CollectorPointPageReqVO reqVO);

    List<CollectorPointDO> getPointsByFrequency(Integer frequency,List<Long> collectorIds);

    List<CollectorPointDO> getPointByCollectorId(Long collectorId);

    int updateCollectorState(Long id, Integer state);

    List<CollectorDO> getCollectorByRunningState(int state);

    List<CollectorPointDO> getPointsByCollectorIds(List<Long> collectorIds);
}
