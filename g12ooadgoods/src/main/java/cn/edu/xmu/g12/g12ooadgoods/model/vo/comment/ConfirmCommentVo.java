package cn.edu.xmu.g12.g12ooadgoods.model.vo.comment;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("Confirm Comment 传值对象")
public class ConfirmCommentVo {
    @NotNull(message = "conclusion 不得为空")
    private Boolean conclusion;
}
