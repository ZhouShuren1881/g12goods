package cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FlashSalePo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlashSaleBo implements VoObject {
//    "id": 0,
//    "flashDate": "string",
//    "timeSeq": {
//      "id": 0,
//      "beginTime": "string",
//      "endTime": "string",
//      "gmtCreate": "string",
//      "gmtModified": "string"
//    },
//    "gmtCreate": "string",
//    "gmtModified": "string"

    Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime flashDate;
    TimeSegOverview timeSeq;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime gmtModified;

    public FlashSaleBo(FlashSalePo po, TimeSegOverview timeSegOverview) {
        id = po.getId();
        flashDate = po.getFlashDate();
        timeSeq = timeSegOverview;
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
