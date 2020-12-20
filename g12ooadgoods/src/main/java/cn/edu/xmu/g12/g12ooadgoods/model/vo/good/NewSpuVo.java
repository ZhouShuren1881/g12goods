package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("新Spu传值对象")
public class NewSpuVo {
    @NotNull(message = "sn不得为空")
    @Size(min = 1)
    private String name;

    @NotNull(message = "decription不得为空")
    @Size(min = 1)
    private String decription;

    @Nullable
    private String specs;
}
