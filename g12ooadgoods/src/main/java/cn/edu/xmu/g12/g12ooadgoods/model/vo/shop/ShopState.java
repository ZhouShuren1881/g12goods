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
    ShopState(int state) {
        this();
        switch (state) {
            case 0:
                code = 0;
                name = "未审核";
                break;
            case 1:
                code = 1;
                name = "未上线";
                break;
            case 2:
                code = 2;
                name = "上线";
                break;
            case 3:
                code = 3;
                name = "关闭";
                break;
            default:
                code = 4;
                name = "审核未通过";
                break;
        }
    }

    public static List<ShopState> getAllStates() {
        var s = new ArrayList<ShopState>();
        for (int i = 0; i <= 4; i++)
            s.add(new ShopState(i));
        return s;
    }
}
