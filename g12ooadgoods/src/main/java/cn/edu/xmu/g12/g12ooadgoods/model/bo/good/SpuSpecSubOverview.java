package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import lombok.Data;

import java.util.List;

@Data
public class SpuSpecSubOverview {
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
    private Long id;
    private String name;
    private List<IdNameOverview> specItems;
}
