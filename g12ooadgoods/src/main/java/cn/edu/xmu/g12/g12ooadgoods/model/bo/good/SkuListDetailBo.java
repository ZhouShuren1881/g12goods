package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SkuListDetailBo implements VoObject {
    //    "page": 0,
    //    "pageSize": 0,
    //    "total": 0,
    //    "pages": 0,
    //    "list": [
    //      {
    //        "id": 0,
    //        "name": "string",
    //        "skuSn": "string",
    //        "imageUrl": "string",
    //        "inventory": 0,
    //        "originalPrice": 0,
    //        "price": 0,
    //        "disable": false
    //      }
    //    ]
    protected Integer page;
    protected Integer pageSize;
    protected Long total;
    protected Integer pages;
    protected List<SkuOverview> list;

    public SkuListDetailBo() { }

    public SkuListDetailBo(Integer page, Integer pageSize, Long total, Integer pages,
                           List<SkuOverview> skuOverviewList) {
        this();
        this.page       = page;
        this.pageSize   = pageSize;
        this.total      = total;
        this.pages      = pages;
        this.list       = skuOverviewList;
    }

    public Object createVo() {return this;}

    public Object createSimpleVo() {return this;}
}
