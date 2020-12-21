package cn.edu.xmu.g12.g12ooadgoods.model.bo.groupon;

import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SpuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GrouponActivityPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GrouponBo {
//    "id": 0,
//    "name": "string",
//    "goodsSpu": {
//      "id": 0,
//      "name": "string",
//      "goodsSn": "string",
//      "imageUrl": "string",
//      "state": 0,
//      "gmtCreate": "string",
//      "gmtModified": "string",
//      "disable": false
//    },
//    "shop": {
//      "id": 0,
//      "name": "string"
//    },
//    "strategy": "string",
//    "state": 0,
//    "beginTime": "string",
//    "endTime": "string",
//    "gmtCreate": "string",
//    "gmtModified": "string"

    private Long id;
    private String name;
    private SpuOverview goodsSpu;
    private IdNameOverview shop;
    private String strategy;
    private Byte state;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    public GrouponBo(GrouponActivityPo po, SpuOverview spuOverview, IdNameOverview shopOverview) {
        id = po.getId();
        name = po.getName();
        goodsSpu = spuOverview;
        shop = shopOverview;
        strategy = po.getStrategy();
        state = po.getState();
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
