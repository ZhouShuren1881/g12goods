package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import lombok.Data;

@Data
public class SkuOverview implements VoObject {
    //    "page": 0,
    //    "pageSize": 0,
    //    "total": 0,
    //    "pages": 0,
    //    "list": [
    //      {
    //        "id": 0,
    //        "name": "string",
    //        "skuSn": "string",
    //        "imageUrl": "string",
    //        "inventory": 0,
    //        "originalPrice": 0,
    //        "price": 0,
    //        "disable": false
    //      }
    //    ]
    Long    id;
    String  name;
    String  skuSn;
    String  imageUrl;
    Integer inventory;
    Long    originalPrice;
    Long    price;
    Boolean disable;

    public SkuOverview() {}

    public SkuOverview(GoodsSkuPo po, Long price) {
        id              = po.getId();
        name            = po.getName();
        skuSn           = po.getSkuSn();
        imageUrl        = po.getImageUrl();
        originalPrice   = po.getOriginalPrice();
        inventory       = po.getInventory();
        this.price      = price;
        disable         = po.getDisabled() != 0;
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
