package cn.edu.xmu.g12.g12ooadgoods.model.vo.comment;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class NewCommentVo {
    @NotNull(message = "type 不得为空")
    private Byte type;
    @NotNull(message = "content 不得为空")
    @Min(1)
    private String content; // 0好评1中评2差评
}
