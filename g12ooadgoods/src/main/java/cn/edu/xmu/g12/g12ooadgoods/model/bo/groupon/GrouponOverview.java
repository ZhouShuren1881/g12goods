package cn.edu.xmu.g12.g12ooadgoods.model.bo.groupon;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GrouponActivityPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GrouponOverview {
    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    public GrouponOverview(GrouponActivityPo po) {
        id = po.getId();
        name = po.getName();
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
    }
}
