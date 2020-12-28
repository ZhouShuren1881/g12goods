package cn.edu.xmu.g12.g12ooadgoods.model.vo.shop;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("店铺名传值对象")
public class ShopNameVo {
    private String name;

    public boolean isInvalid() {
        if (name == null) return true;
        name = name.trim();
        return name.length() == 0;
    }
}
