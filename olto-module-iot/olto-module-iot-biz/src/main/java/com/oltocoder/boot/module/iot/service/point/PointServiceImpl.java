package com.oltocoder.boot.module.iot.service.point;

import com.oltocoder.boot.framework.common.pojo.PageResult;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointCreateReqVO;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointPageReqVO;
import com.oltocoder.boot.module.iot.controller.admin.point.vo.PointUpdateReqVO;
import com.oltocoder.boot.module.iot.convert.point.PointConvert;
import com.oltocoder.boot.module.iot.dal.dataobject.point.PointDO;
import com.oltocoder.boot.module.iot.dal.mysql.point.PointMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pointService")
public class PointServiceImpl implements  PointService {

    @Resource
    private PointMapper pointMapper;

    @Override
    public Long createPoint(PointCreateReqVO reqVO) {
        PointDO pointDO = PointConvert.INSTANCE.convert01(reqVO);
        int count = pointMapper.insert(pointDO);
        return count>0 ? pointDO.getId() : 0L;
    }

    @Override
    public int updatePoint(PointUpdateReqVO reqVO) {
        PointDO pointDO = PointConvert.INSTANCE.convert02(reqVO);
        return pointMapper.updateById(pointDO);
    }

    @Override
    public int deletePoint(Long id) {
        return pointMapper.deleteById(id);
    }

    @Override
    public PointDO getPoint(Long id) {
        return pointMapper.selectById(id);
    }

    @Override
    public PageResult<PointDO> getPointPage(PointPageReqVO reqVO) {
        return pointMapper.pageResult(reqVO);
    }

    @Override
    public int updatePointEnable(Long id, boolean enabled) {
        PointDO pointDO = getPoint(id);
        pointDO.setEnabled(enabled);
        return pointMapper.updateById(pointDO);
    }


    @Override
    public List<PointDO> getPoints(List<Long> ids) {
        return pointMapper.selectBatchIds(ids);
    }

    @Override
    public List<PointDO> getPointsByDeviceId(Long deviceId) {
        return pointMapper.getPointsByDeviceId(deviceId);
    }
}
