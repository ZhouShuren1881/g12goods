package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("新品牌传值对象")
public class NewBrandVo {
    @NotNull(message = "name不得为空")
    @Size(min = 1)
    private String name;

    @Nullable
    private String detail;

    public boolean isInvalid() {
        name = name.trim();
        if (detail != null) detail = detail.trim();
        return name.isEmpty();
    }
}
