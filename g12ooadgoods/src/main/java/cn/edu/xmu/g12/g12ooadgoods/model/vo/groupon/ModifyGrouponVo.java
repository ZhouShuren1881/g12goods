package cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GrouponActivityPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
public class ModifyGrouponVo {
    @Nullable
    private String strategy;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @Nullable
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    public boolean isInvalid(GrouponActivityPo po) {
        if (strategy != null) {
            strategy = strategy.trim();
            if (strategy.isEmpty()) return true;
        }
        var stime = beginTime==null?po.getBeginTime():beginTime;
        var etime = endTime==null?po.getEndTime():endTime;
        if (stime != null && etime != null && !beginTime.isBefore(endTime)) return true;

        return false;
    }
}
