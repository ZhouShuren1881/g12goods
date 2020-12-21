package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import lombok.Data;

import java.util.List;

@Data
public class SpecOverview {
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
