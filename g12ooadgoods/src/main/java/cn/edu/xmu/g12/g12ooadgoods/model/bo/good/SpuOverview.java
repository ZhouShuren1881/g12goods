package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpuOverview {

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
      private LocalDateTime gmtCreate;
      private LocalDateTime gmtModified;
      private Boolean disable;

      private SpuBrandSubOverView   brand;
      private IdNameOverview        category;
      private SpuFreightSubOverview freight;
      private IdNameOverview        shop;
      private SpuSpecSubOverview    spec;
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

      public SpuOverview() { }

      public SpuOverview(GoodsSpuPo             po,
                         BrandPo                brand,
                         GoodsCategoryPo        category,
                         FreightModelPo         freight,
                         ShopPo                 shop,
                         SpuSpecSubOverview     spec,
                         List<SkuOverview> skuList) {
            this();
            this.copyFromPo(po);
            this.brand = new SpuBrandSubOverView(brand);
            this.category = new IdNameOverview(category.getId(), category.getName());
            this.freight = new SpuFreightSubOverview(freight);
            this.shop = new IdNameOverview(shop.getId(), shop.getName());
            this.spec = spec;
            this.skuList = skuList;
      }
}
