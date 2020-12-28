package cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon;

import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime beginTime;

    @Nullable
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime end_time;

    @Nullable
    private String strategy;

    public boolean isInvalid(CouponActivityPo po) {
        if (Tool.allNull(name, quantity, beginTime, end_time, strategy))
            return true;

        if (name != null) {
            name = name.trim();
            if (name.length() == 0) return true;
        }
        if (quantity != null && quantity < 0) return true;

        var stime = beginTime==null?po.getBeginTime():beginTime;
        var etime = end_time==null?po.getEndTime():end_time;
        if (stime != null && etime != null && !stime.isBefore(etime)) return true;

        return false;
    }

}
