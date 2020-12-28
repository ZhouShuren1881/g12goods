package cn.edu.xmu.g12.g12ooadgoods.model.vo.presale;

import cn.edu.xmu.g12.g12ooadgoods.model.po.PresaleActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Data
public class ModifyPreSaleVo {
//  "name": "string",
//  "advancePayPrice": 0,
//  "restPayPrice": 0,
//  "quantity": 0,
//  "beginTime": "string",
//  "payTime": "string",
//  "endTime": "string"

    @Nullable
    private String name;
    @Nullable
    private Long advancePayPrice;
    @Nullable
    private Long restPayPrice;
    @Nullable
    private Integer quantity;
    @Nullable
    private LocalDateTime beginTime;
    @Nullable
    private LocalDateTime payTime;
    @Nullable
    private LocalDateTime endTime;

    public boolean isInvalid(PresaleActivityPo po) {
        if (Tool.allNull(name, advancePayPrice, restPayPrice, quantity,
                beginTime, payTime, endTime)) return true;

        if (name != null) {
            name = name.trim();
            if (name.length() == 0) return true;
        }
        if (advancePayPrice != null && advancePayPrice < 0) return true;
        if (restPayPrice != null && restPayPrice < 0) return true;
        if (quantity != null && quantity <= 0) return true;

        var stime = beginTime==null?po.getBeginTime():beginTime;
        var ptime = payTime==null?po.getPayTime():payTime;
        var etime = endTime==null?po.getEndTime():endTime;
        if (stime != null && ptime != null && etime != null &&
                !(stime.isBefore(ptime) && ptime.isBefore(etime))) return true;

        return false;
    }
}