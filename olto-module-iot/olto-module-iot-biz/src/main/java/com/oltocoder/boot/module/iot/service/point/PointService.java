package com.oltocoder.boot.module.iot.service.point;

import cn.hutool.core.collection.CollUtil;
import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.framework.common.util.collection.CollectionUtils;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointUpdateReqVO;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface PointService {
    Long createPoint(PointCreateReqVO reqVO);

    int updatePoint(PointUpdateReqVO reqVO);

    int deletePoint(Long id);

    PointDO getPoint(Long id);

    PageResult<PointDO> getPointPage(PointPageReqVO reqVO);

    int updatePointEnable(Long id, boolean enabled);

    default Map<Long,PointDO> getPointMap(List<Long> ids){
        if(CollUtil.isEmpty(ids))
            return new HashMap<>();
        List<PointDO> points = getPoints(ids);
        return CollectionUtils.convertMap(points, PointDO::getId);
    }

    List<PointDO> getPoints(List<Long> ids);

    List<PointDO> getPointsByDeviceId(Long deviceId);
}
