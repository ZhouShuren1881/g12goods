package cn.edu.xmu.g12.g12ooadgoods.model.vo.presale;

import cn.edu.xmu.g12.g12ooadgoods.model.po.PresaleActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class NewPreSaleVo {
//  "name": "string",
//  "advancePayPrice": 0,
//  "restPayPrice": 0,
//  "quantity": 0,
//  "beginTime": "string",
//  "payTime": "string",
//  "endTime": "string"

    private String name;
    private Long advancePayPrice;
    private Long restPayPrice;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime payTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;

    public boolean isInvalid() {
        if (name == null) return true;
        name = name.trim();
        if (name.length() == 0) return true;
        if (advancePayPrice == null || advancePayPrice < 0) return true;
        if (restPayPrice == null || restPayPrice < 0) return true;
        if (quantity == null || quantity <= 0) return true;
        if (!(beginTime.isBefore(payTime) && payTime.isBefore(endTime))) return true;

        return false;
    }
}
