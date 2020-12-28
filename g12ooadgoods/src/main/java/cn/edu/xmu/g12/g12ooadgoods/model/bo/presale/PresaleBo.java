package cn.edu.xmu.g12.g12ooadgoods.model.bo.presale;

import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.PresaleActivityPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PresaleBo {
//        "id": 0,
//        "name": "string",
//        "BeginTime": "string",
//        "payTime": "string",
//        "endTime": "string",
//        "goodsSku": {
//          "id": 0,
//          "name": "string",
//          "skuSn": "string",
//          "imageUrl": "string",
//          "inventory": 0,
//          "originalPrice": 0,
//          "price": 0,
//          "disable": false
//        },
//        "shop": {
//          "id": 0,
//          "name": "string"
//        },
//        "state": "string",
//        "quantity": 0,
//        "advancePayPrice": 0,
//        "restPayPrice": 0,
//        "gmtCreate": "string",
//        "gmtModified": "string"

    private Long id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime BeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime payTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private SkuOverview goodsSku;
    private IdNameOverview shop;
    private Byte state;
    private Integer quantity;
    private Long advancePayPrice;
    private Long restPayPrice;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    public PresaleBo(PresaleActivityPo po, SkuOverview skuOverview, IdNameOverview shopOverview) {
        id          = po.getId();
        name        = po.getName();
        BeginTime   = po.getBeginTime();
        payTime     = po.getPayTime();
        endTime     = po.getEndTime();
        goodsSku    = skuOverview;
        shop        = shopOverview;
        state       = po.getState();
        quantity    = po.getQuantity();
        advancePayPrice = po.getAdvancePayPrice();
        restPayPrice    = po.getRestPayPrice();
        gmtCreate   = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
