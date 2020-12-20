package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SkuBo implements VoObject {
//    "id": 0,
//    "name": "string",
//    "skuSn": "string",
//    "detail": "string",
//    "imageUrl": "string",
//    "originalPrice": 0,
//    "price": 0,
//    "inventory": 0,
//    "state": 0,
//    "configuration": "string",
//    "weight": 0,
//    "gmtCreate": "string",
//    "gmtModified": "string",
//    "disable": false,
//    "shareable": false,
//    "spu" : { ... }

    private Long id;
    private String name;
    private String skuSn;
    private String detail;
    private String imageUrl;
    private Long originalPrice;
    private Long price;
    private Integer inventory;
    private Byte state;
    private String configuration;
    private Long weight;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;
    private Boolean disable;
    private Boolean shareable;

    private SpuOverview spu;

    void copyFromPo(GoodsSkuPo po) {
        id = po.getId();
        name = po.getName();
        skuSn = po.getSkuSn();
        detail = po.getDetail();
        imageUrl = po.getImageUrl();
        originalPrice = po.getOriginalPrice();
        inventory = po.getInventory();
        state = po.getState();
        configuration = po.getConfiguration();
        weight = po.getWeight();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
        disable = po.getDisabled() != 0;

        // Miss Price and shareable
    }

    public SkuBo() { }

    public SkuBo(GoodsSkuPo po, Long price, Boolean shareable, SpuOverview spu) {
        this();
        this.copyFromPo(po);
        this.setPrice(price);
        this.setShareable(shareable);
        this.setSpu(spu);
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
