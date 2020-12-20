package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.BrandPo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandBo {
    private Long id;
    private String name;
    private String imageUrl;
    private String detail;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public BrandBo() { }

    public BrandBo(BrandPo po) {
        id          = po.getId();
        name        = po.getName();
        imageUrl    = po.getImageUrl();
        detail      = po.getDetail();
        gmtCreate   = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
