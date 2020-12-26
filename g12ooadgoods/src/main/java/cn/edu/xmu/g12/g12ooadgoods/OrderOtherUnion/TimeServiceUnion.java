package cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion;

import cn.edu.xmu.g12.g12ooadgoods.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.TimeSegOverview;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import cn.edu.xmu.oomall.other.model.TimeDTO;
import cn.edu.xmu.oomall.other.service.ITimeService;
import com.alibaba.nacos.api.annotation.NacosInjected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeServiceUnion {

    @NacosInjected
    ITimeService timeService;
    @Autowired(required = false)
    TimeSegmentPoMapper timeSegmentPoMapper;

    public ReturnObject<TimeDTO> getTimeSegmentId(Long id) {
        return new Trans<TimeDTO>().oomall(timeService.getTimeSegmentId((byte)1, id));
    }

    public ReturnObject<Long> getCurrentSegmentId() {
        return  new Trans<Long>().oomall(timeService.getCurrentSegmentId((byte)1));
    }

    /**
     * To be removed....
     */
    public TimeSegOverview getTimeSegOverviewById(Long timesegId) {
        var timesegPo = timeSegmentPoMapper.selectByPrimaryKey(timesegId);
        if (timesegPo == null) return null;
        return new TimeSegOverview(timesegPo);
    }
}
