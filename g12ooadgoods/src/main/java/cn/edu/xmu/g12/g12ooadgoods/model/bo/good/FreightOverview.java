package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.model.po.FreightModelPo;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FreightOverview { // order -> freight_model
//      "freight": {
//        "id": 0,
//        "name": "string",
//        "type": 0,
//        "unit": 0,
//        "default": true,
//        "gmtCreate": "string",
//        "gmtModified": "string"
//      },
    private Long id;
    private String name;
    private Byte type;
    private Integer unit;
    @JSONField(name="default") // 转json时显示为default
    private Boolean defaultModel;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    public FreightOverview() { }
    public FreightOverview(FreightModelPo po) {
        this();
        id = po.getId();
        name = po.getName();
        type = po.getType();
        unit = po.getUnit();
        defaultModel = po.getDefaultModel() != 0;
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
