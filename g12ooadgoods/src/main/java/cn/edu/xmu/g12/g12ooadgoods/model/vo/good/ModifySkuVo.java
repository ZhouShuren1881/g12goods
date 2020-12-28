package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
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

    public boolean isInvalid() {
        if (Tool.allNull(name, originalPrice, configuration, weight, inventory, detail)) return true;
        if (name != null) {
            name = name.trim();
            if (name.isEmpty()) return true;
        }
        if (originalPrice != null && originalPrice <= 0) return true;
        if (configuration != null) {
            configuration = configuration.trim();
            if (configuration.isEmpty()) return true;
        }
        if (weight != null && weight <= 0) return true;
        if (inventory != null && inventory < 0) return true;
        if (detail != null) {
            detail = detail.trim();
            if (detail.isEmpty()) return true;
        }
        return false;
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
