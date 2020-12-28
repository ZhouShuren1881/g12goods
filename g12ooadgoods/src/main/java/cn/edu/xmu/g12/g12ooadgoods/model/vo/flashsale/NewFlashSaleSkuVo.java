package cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("新FlashSale Sku传值对象")
public class NewFlashSaleSkuVo {
    @NotNull(message = "skuId 不得为空")
    @Min(1)
    private Long skuId;

    @NotNull(message = "price 不得为空")
    @Min(1)
    private Long price;

    @NotNull(message = "quantity 不得为空")
    @Min(1)
    private Integer quantity;
}
