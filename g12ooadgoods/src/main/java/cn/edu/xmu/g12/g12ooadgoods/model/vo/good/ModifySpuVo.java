package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("修改Spu传值对象")
public class ModifySpuVo {
    @Nullable
    private String name;

    @Nullable
    private String decription;

    @Nullable
    private String specs;
}
