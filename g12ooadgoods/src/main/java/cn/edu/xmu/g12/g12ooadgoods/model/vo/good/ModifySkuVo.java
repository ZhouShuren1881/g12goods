package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("新SKU传值对象")
public class ModifySkuVo {
//    {
//            "name": "string",
//            "originalPrice": 0,
//            "configuration": "string",
//            "weight": 0,
//            "imageUrl": "string",
//            "inventory": 0,
//            "detail": "string"
//    }

    @Nullable
    private String name;
    @Nullable
    private Long originalPrice;
    @Nullable
    private String configuration;
    @Nullable
    private Long weight;
    @Nullable
    private Integer inventory;
    @Nullable
    private String detail;

    public boolean isAllFieldNull() {
        if (name            != null) return false;
        if (originalPrice   != null) return false;
        if (configuration   != null) return false;
        if (weight          != null) return false;
        if (inventory       != null) return false;
        if (detail          != null) return false;
        return true;
    }

    public GoodsSkuPo convertToGoodsSkuPo() {
        GoodsSkuPo po = new GoodsSkuPo();
        po.setName(name);
        po.setOriginalPrice(originalPrice);
        po.setConfiguration(configuration);
        po.setWeight(weight);
        po.setInventory(inventory);
        po.setDetail(detail);
        return po;
    }
}
