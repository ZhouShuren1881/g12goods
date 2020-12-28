package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsCategoryPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryBo implements VoObject {
    private Long id;
    private Long pid;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    public CategoryBo() {
    }

    public CategoryBo(GoodsCategoryPo po) {
        this();
        id = po.getId();
        pid = po.getPid();
        name = po.getName();
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }

    public Object createVo() { return this; }

    public Object createSimpleVo() { return this; }
}
