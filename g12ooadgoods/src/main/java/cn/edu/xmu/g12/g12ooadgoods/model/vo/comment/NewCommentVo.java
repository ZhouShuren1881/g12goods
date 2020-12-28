package cn.edu.xmu.g12.g12ooadgoods.model.vo.comment;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class NewCommentVo {
    private Byte type; // 0好评1中评2差评
    private String content;

    public boolean isInvalid() {
        if (type == null || type < 0 || type > 2) return true;
        if (content == null) return true;
        content = content.trim();
        return content.isEmpty();
    }
}
