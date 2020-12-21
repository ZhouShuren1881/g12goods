package cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale;

import cn.edu.xmu.g12.g12ooadgoods.model.po.TimeSegmentPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class TimeSeqOverview {
//      "id": 0,
//      "beginTime": "string",
//      "endTime": "string",
//      "gmtCreate": "string",
//      "gmtModified": "string"

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    public TimeSeqOverview() { }

    public TimeSeqOverview(TimeSegmentPo po) {
        id = po.getId();
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
