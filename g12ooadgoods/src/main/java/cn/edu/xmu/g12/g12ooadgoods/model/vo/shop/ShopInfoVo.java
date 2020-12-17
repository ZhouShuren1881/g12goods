package cn.edu.xmu.g12.g12ooadgoods.model.vo.shop;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopInfoVo implements VoObject {
//        {
//            "errno": 0,
//                "errmsg": "成功",
//                "data": {
//                    "id": 0,
//                    "name": "string",
//                    "state": 0,
//                    "gmtCreate": "string",
//                    "gmtModified": "string"
//                }
//        }
    private Long id;
    private String name;
    private Byte state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public Object createVo() {
        return this;
    }

    public Object createSimpleVo() {
        return this;
    }
}
