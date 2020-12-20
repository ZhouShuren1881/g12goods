package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("新SKU传值对象")
public class NewSkuVo {
//    {
//            "sn": "string",
//            "name": "string",
//            "originalPrice": 0,
//            "configuration": "string",
//            "weight": 0,
//            "imageUrl": "string",
//            "inventory": 0,
//            "detail": "string"
//    }

    @NotNull(message = "sn不得为空")
    @Size(min = 1)
    private String sn;

    @NotNull(message = "name不得为空")
    @Size(min = 1)
    private String name;

    @NotNull
    @Min(0)
    private Long originalPrice;

    @NotNull(message = "configuration不得为空")
    @Size(min = 1)
    private String configuration;

    @NotNull
    @Min(0)
    private Long weight;

    @NotNull(message = "imageUrl不得为空")
    @Size(min = 1)
    private String imageUrl;

    @NotNull
    @Min(0)
    private Integer inventory;

    @NotNull(message = "detail不得为空")
    @Size(min = 1)
    private String detail;
}
