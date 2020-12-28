package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
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

    public boolean isInvalid() {
        if (Tool.allNull(name, detail)) return true;
        if (name != null) {
            name = name.trim();
            if (name.isEmpty()) return true;
        }
        if (detail != null) {
            detail = detail.trim();
            if (detail.isEmpty()) return true;
        }
        return false;
    }
}