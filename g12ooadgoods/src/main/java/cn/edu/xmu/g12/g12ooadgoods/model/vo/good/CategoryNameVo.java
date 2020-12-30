package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("新Category传值对象")
public class CategoryNameVo {
    @NotNull(message = "name不得为空")
    @Size(min = 1)
    private String name;

    public boolean isInvalid() {
        name = name.trim();
        return name.length() == 0;
    }
}
