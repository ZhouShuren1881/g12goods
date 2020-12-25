package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpuBo implements VoObject {

//    "spu": {
//      "id": 0,
//      "name": "string",
//      "goodsSn": "string",
//      "detail": "string",
//      "imageUrl": "string",
//      "gmtCreate": "string",
//      "gmtModified": "string",
//      "disable": false
//      "brand": {
//        "id": 0,
//        "name": "string",
//        "imageUrl": "string"
//      },
//      "category": {
//        "id": 0,
//        "name": "string"
//      },
//      "freight": {
//        "id": 0,
//        "name": "string",
//        "type": 0,
//        "unit": 0,
//        "default": true,
//        "gmtCreate": "string",
//        "gmtModified": "string"
//      },
//      "shop": {
//        "id": 0,
//        "name": "string"
//      },
//      "spec": {
//        "id": 0,
//        "name": "string",
//        "specItems": [
//          {
//            "id": 0,
//            "name": "string"
//          }
//        ]
//      },
//      "skuList": [
//        {
//          "id": 0, "name": "string", "skuSn": "string", "imageUrl": "string",
//          "inventory": 0, "originalPrice": 0, "price": 0, "disable": false
//        }
//      ]
//    }

      private Long id;
      private String name;
      private String goodsSn;
      private String detail;
      private String imageUrl;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private LocalDateTime gmtCreate;
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private LocalDateTime gmtModified;
      private Boolean disable;

      private BrandOverview   brand;
      private IdNameOverview  category;
      private FreightOverview freight;
      private IdNameOverview  shop;
      private SpecOverview    spec;
      private List<SkuOverview>     skuList;


      void copyFromPo(GoodsSpuPo po) {
            id = po.getId();
            name = po.getName();
            goodsSn = po.getGoodsSn();
            detail = po.getDetail();
            imageUrl = po.getImageUrl();
            gmtCreate = po.getGmtCreate();
            gmtModified = po.getGmtModified();
            disable = po.getDisabled() != 0;
      }

      public SpuBo() { }

      public SpuBo(GoodsSpuPo             po,
                   BrandPo                brand,
                   GoodsCategoryPo        category,
                   FreightModelPo         freight,
                   ShopPo                 shop,
                   SpecOverview spec,
                   List<SkuOverview> skuList) {
            this();
            this.copyFromPo(po);
            this.brand =      brand == null ? null : new BrandOverview(brand);
            this.category =   category == null ? null : new IdNameOverview(category.getId(), category.getName());
            this.freight =    freight == null ? null : new FreightOverview(freight);
            this.shop =       shop == null ? null : new IdNameOverview(shop.getId(), shop.getName());
            this.spec = spec;
            this.skuList = skuList;
      }

      public Object createVo() { return this; }

      public Object createSimpleVo() { return this; }
}
