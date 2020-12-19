package cn.edu.xmu.g12.g12ooadgoods.model.vo.shop;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("店铺审核状态传值对象")
public class NewShopAuditVo {
    @NotNull(message = "审核结果不得为空")
    private Boolean conclusion;
}
