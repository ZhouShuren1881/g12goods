package cn.edu.xmu.g12.g12ooadgoods.model.vo.shop;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    public Object createVo() {
        return this;
    }

    public Object createSimpleVo() {
        return this;
    }
}
