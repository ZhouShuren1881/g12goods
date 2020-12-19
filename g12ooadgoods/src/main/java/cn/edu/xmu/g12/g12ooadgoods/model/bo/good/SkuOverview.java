package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import lombok.Data;

@Data
public class SkuOverview {
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

    public SkuOverview(GoodsSkuPo goodsSkuPo, Long price) {
        id              = goodsSkuPo.getId();
        name            = goodsSkuPo.getName();
        skuSn           = goodsSkuPo.getSkuSn();
        imageUrl        = goodsSkuPo.getImageUrl();
        originalPrice   = goodsSkuPo.getOriginalPrice();
        this.price      = price;
        disable         = goodsSkuPo.getDisabled() != 0;
    }
}
