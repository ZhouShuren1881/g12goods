package cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon;

import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.UserOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponActivityPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CouponActivityBo {
//    "id": 0,
//    "name": "string",
//    "state": 0,
//    "shop": {
//      "id": 0,
//      "name": "string"
//    },
//    "quantity": 0,
//    "quantityType": 0,
//    "validTerm": 0,
//    "imageUrl": "string",
//    "beginTime": "string",
//    "endTime": "string",
//    "couponTime": "string",
//    "strategy": "string",
//    "createdBy": {
//      "id": 0,
//      "userName": "string"
//    },
//    "ModiBy": {
//      "id": 0,
//      "userName": "string"
//    },
//    "gmtCreate": "string",
//    "gmtModified": "string"

    private Long id;
    private String name;
    private IdNameOverview shop;
    private Integer quantity;
    private Byte quantityType;
    private Byte validTerm;
    private String imageUrl;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime couponTime;
    private String strategy;
    private UserOverview createdBy;
    private UserOverview ModiBy;
    private Byte state;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    public CouponActivityBo() { }
    public CouponActivityBo(CouponActivityPo po, IdNameOverview shopOverview, UserOverview createUser, UserOverview modiUser) {
        id = po.getId();
        name = po.getName();
        shop = shopOverview;
        quantity = po.getQuantity();
        quantityType = po.getQuantitiyType();
        validTerm = po.getValidTerm();
        imageUrl = po.getImageUrl();
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
        couponTime = po.getCouponTime();
        strategy = po.getStrategy();
        createdBy = createUser;
        ModiBy = modiUser;
        state = po.getState();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
