package cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon;

import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponActivityOverview {
    private Long id;
    private String name;
    private String imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime couponTime;

    public CouponActivityOverview(CouponActivityPo po) {
        id  = po.getId();
        name    = po.getName();
        imageUrl    = po.getImageUrl();
        beginTime   = po.getBeginTime();
        endTime     = po.getEndTime();
        quantity    = po.getQuantity();
        couponTime  = po.getCouponTime();
    }
}
