package cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@ApiModel("新 Coupon 传值对象")
public class NewCouponVo {
//  "name": "string",
//  "quantity": 0,
//  "quantityType": 0,
//  "validTerm": 0,
//  "couponTime": "string",
//  "beginTime": "string",
//  "endTime": "string",
//  "strategy": "string"

    @NotNull(message = "name 不得为空")
    @Size(min = 1)
    private String name;

    @NotNull(message = "quantity 不得为空")
    @Min(0)
    private Integer quantity;

    @NotNull(message = "quantityType 不得为空")
    @Min(0)
    @Max(1)
    private Byte quantityType;

    @NotNull(message = "validTerm 不得为空")
    @Min(0)
    @Max(4)
    private Byte validTerm;

    @NotNull(message = "couponTime 不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime couponTime;

    @NotNull(message = "beginTime 不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime beginTime;

    @NotNull(message = "endTime 不得为空")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime endTime;

    @NotNull(message = "strategy 不得为空")
    @Size(min = 1)
    private String strategy;

    public boolean isInvalid() {
        name = name.trim();
        if (name.length() == 0) return true;
        if (!(couponTime.isBefore(beginTime)&&beginTime.isBefore(endTime)))
            return true;
        strategy = strategy.trim();
        if (strategy.isEmpty()) return true;

        return false;
    }

}
