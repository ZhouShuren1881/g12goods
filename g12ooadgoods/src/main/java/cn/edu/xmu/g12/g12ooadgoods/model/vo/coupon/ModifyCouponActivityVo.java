package cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon;

import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
public class ModifyCouponActivityVo {
//  "name": "string",
//  "quantity": 0,
//  "beginTime": "string",
//  "end_time": "string",
//  "strategy": "string"

    @Nullable
    private String name;

    @Nullable
    private Integer quantity;

    @Nullable
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    @Nullable
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @Nullable
    private String strategy;

    public boolean isInvalid(CouponActivityPo po) {
        if (Tool.allNull(name, quantity, beginTime, endTime, strategy))
            return true;

        if (name != null) {
            name = name.trim();
            if (name.length() == 0) return true;
        }
        if (quantity != null && quantity < 0) return true;

        var stime = beginTime==null?po.getBeginTime():beginTime;
        var etime = endTime ==null?po.getEndTime(): endTime;
        if (stime != null && etime != null && !stime.isBefore(etime)) return true;

        return false;
    }

}
