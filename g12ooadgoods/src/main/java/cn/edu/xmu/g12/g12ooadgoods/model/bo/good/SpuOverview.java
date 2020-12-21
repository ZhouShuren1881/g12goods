package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSpuPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SpuOverview {
//      "id": 0,
//      "name": "string",
//      "goodsSn": "string",
//      "imageUrl": "string",
//      "state": 0,
//      "gmtCreate": "string",
//      "gmtModified": "string",
//      "disable": false

    private Long id;
    private String name;
    private String goodsSn;
    private String imageUrl;
    private Byte state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;
    private Boolean disable;

    public SpuOverview(GoodsSpuPo po) {
        id = po.getId();
        name = po.getName();
        goodsSn = po.getGoodsSn();
        imageUrl = po.getImageUrl();
        state = 0;
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
        disable = po.getDisabled() != 0;
    }
}
