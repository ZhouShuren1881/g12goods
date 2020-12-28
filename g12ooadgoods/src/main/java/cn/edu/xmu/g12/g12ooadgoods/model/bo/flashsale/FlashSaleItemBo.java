package cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FlashSaleItemPo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FlashSalePo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlashSaleItemBo implements VoObject {

//  "id": 0,
//  "goodsSku": {
//    "id": 0,
//    "name": "string",
//    "skuSn": "string",
//    "imageUrl": "string",
//    "inventory": 0,
//    "originalPrice": 0,
//    "price": 0,
//    "disable": false
//  },
//  "price": 0,
//  "quantity": 0,
//  "gmtCreate": "string",
//  "gmtModified": "string"

    private Long id;
    private Long price;
    private Integer quantity;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    private SkuOverview goodsSku;

    public FlashSaleItemBo() { }

    public FlashSaleItemBo(FlashSaleItemPo po, SkuOverview sku) {
        id = po.getId();
        price = po.getPrice();
        quantity = po.getQuantity();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
        goodsSku = sku;
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
