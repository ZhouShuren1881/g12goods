package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.BrandPo;
import lombok.Data;

@Data
public class BrandOverview {
//      "category": {
//        "id": 0,
//        "name": "string"
//      },
    private Long id;
    private String name;
    private String imageUrl;

    public BrandOverview(BrandPo po) {
        id = po.getId();
        name = po.getName();
        imageUrl = po.getImageUrl();
    }
}
