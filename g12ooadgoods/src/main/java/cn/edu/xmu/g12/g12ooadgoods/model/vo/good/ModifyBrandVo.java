package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@ApiModel("修改品牌传值对象")
public class ModifyBrandVo {
    @Nullable
    private String name;

    @Nullable
    private String detail;
}