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

import lombok.Data;

@Data
public class ShopsStatesVo {
    public Integer code;
    public String name;
}
