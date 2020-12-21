package cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon;

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
    private LocalDateTime beginTime;

    @Nullable
    private LocalDateTime end_time;

    @Nullable
    private String strategy;

}
