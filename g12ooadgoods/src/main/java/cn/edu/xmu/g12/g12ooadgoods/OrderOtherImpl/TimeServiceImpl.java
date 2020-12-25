package cn.edu.xmu.g12.g12ooadgoods.OrderOtherImpl;

import cn.edu.xmu.g12.g12ooadgoods.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.po.TimeSegmentPoExample;
import cn.edu.xmu.oomall.other.model.TimeDTO;
import cn.edu.xmu.oomall.other.service.ITimeService;
import cn.edu.xmu.oomall.util.ResponseCode;
import cn.edu.xmu.oomall.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeServiceImpl implements ITimeService {
    @Autowired(required = false)
    TimeSegmentPoMapper timeSegmentPoMapper;

    public ReturnObject<List<Long>> listSelectAllTimeSegmentId(Byte type) {
        return null;
    }

    public ReturnObject<TimeDTO> getTimeSegmentId(Byte type, Long id) {
        if (type == 1) {
            var po = timeSegmentPoMapper.selectByPrimaryKey(id);
            var timeDTO = new TimeDTO(id, type, po.getBeginTime().toLocalTime(), po.getEndTime().toLocalTime());
            return new ReturnObject<>(timeDTO);
        }
        // 忽略广告时段
        return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
    }

    public ReturnObject<Long> getCurrentSegmentId(Byte type) {
        if (type == 1) {
            var now = LocalTime.now();
            var example = new TimeSegmentPoExample();
            example.createCriteria().andBeginTimeIsNotNull().andTypeEqualTo(type);
            var timesegList = timeSegmentPoMapper.selectByExample(example);
            var nowList = timesegList.stream().filter(
                    item ->
                            !item.getBeginTime().toLocalTime().isAfter(now)
                                    && !item.getEndTime().toLocalTime().isBefore(now)
            ).collect(Collectors.toList());
            if (nowList.size() == 0) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            return new ReturnObject<>(nowList.get(0).getId());
        }
        // 忽略广告时段
        return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
    }
}
