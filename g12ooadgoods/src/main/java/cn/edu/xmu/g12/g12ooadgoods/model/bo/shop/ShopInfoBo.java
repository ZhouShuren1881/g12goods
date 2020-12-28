package cn.edu.xmu.g12.g12ooadgoods.model.bo.shop;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.ShopPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShopInfoBo {
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    public ShopInfoBo(ShopPo po) {
        id = po.getId();
        name = po.getName();
        state = po.getState();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
