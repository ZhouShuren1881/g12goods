package cn.edu.xmu.g12.g12ooadgoods.model.vo.shop;

//url: /shops/states
//{
//  "errno": 0,
//  "errmsg": "成功",
//  "data": [
//    {
//      "code": 0,
//      "name": "string"
//    }
//  ]
//}
//
//0：未审核，1：未上线，2：上线，3：关闭，4：审核未通过

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShopState {
    private Integer code;
    private String name;

    public ShopState() {}
    public static ShopState newState(int state) {
        var ss = new ShopState();
        switch (state) {
            case 0:
                ss.code = 0;
                ss.name = "未审核";
                break;
            case 1:
                ss.code = 1;
                ss.name = "未上线";
                break;
            case 2:
                ss.code = 2;
                ss.name = "上线";
                break;
            case 3:
                ss.code = 3;
                ss.name = "关闭";
                break;
            default:
                ss.code = 4;
                ss.name = "审核未通过";
                break;
        }
        return ss;
    }

    public List<ShopState> getAllStates() {
        var s = new ArrayList<ShopState>();
        for (int i = 0; i <= 4; i++)
            s.add(ShopState.newState(i));
        return s;
    }
}
