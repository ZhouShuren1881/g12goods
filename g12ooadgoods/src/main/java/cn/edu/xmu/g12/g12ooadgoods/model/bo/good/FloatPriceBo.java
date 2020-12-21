package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.UserOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FloatPricePo;

import java.time.LocalDateTime;

public class FloatPriceBo implements VoObject {
//    "id": 0,
//    "activityPrice": 0,
//    "quantity": 0,
//    "beginTime": "string",
//    "endTime": "string",
//    "valid": true,
//    "gmtCreate": "string",
//    "gmtModified": "string"
//    "createdBy": {
//      "id": 0,
//      "userName": "string"
//    },
//    "modifiedBy": {
//      "id": 0,
//      "userName": "string"
//    },

    private Long id;
    private Long activityPrice;
    private Integer quantity;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Boolean valid;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private UserOverview createdBy;
    private UserOverview modifiedBy;

    public FloatPriceBo() { }

    public FloatPriceBo(FloatPricePo po, UserOverview createdBy, UserOverview modifiedBy) {
        id = po.getId();
        activityPrice = po.getActivityPrice();
        quantity = po.getQuantity();
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
        valid = po.getValid() != 0;
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
