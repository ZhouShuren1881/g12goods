package cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CouponState {
    private Integer code;
    private String name;

    public CouponState() {}

    CouponState(int state) {
        this();
        switch (state) {
            case 0:
                code = 0;
                name = "未领取";
                break;
            case 1:
                code = 1;
                name = "已领取";
                break;
            case 2:
                code = 2;
                name = "已使用";
                break;
            default:
                code = 3;
                name = "已失效";
                break;
        }
    }

    public static List<CouponState> getAllStates() {
        var s = new ArrayList<CouponState>();
        for (int i = 0; i <= 3; i++) s.add(new CouponState(i));
        return s;
    }
}
