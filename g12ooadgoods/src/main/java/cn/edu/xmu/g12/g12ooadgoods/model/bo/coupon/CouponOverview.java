package cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponActivityPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CouponPo;
import lombok.Data;

@Data
public class CouponOverview implements VoObject {
    private Long id;
    private String name;
    private String couponSn;
    private CouponActivityOverview activity;

    public CouponOverview(CouponPo po, CouponActivityPo activityPo) {
        id = po.getId();
        name = po.getName();
        couponSn = po.getCouponSn();
        activity = new CouponActivityOverview(activityPo);
    }

    public Object createVo() {
        return this;
    }

    public Object createSimpleVo() {
        return this;
    }

}
