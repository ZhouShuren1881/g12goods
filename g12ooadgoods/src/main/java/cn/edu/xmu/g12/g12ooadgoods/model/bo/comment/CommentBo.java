package cn.edu.xmu.g12.g12ooadgoods.model.bo.comment;

import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentBo {
//    "id": 0,
//    "customer": {
//      "id": 0,
//      "userName": "string",
//      "name": "string"
//    },
//    "goodsSkuId": 0,
//    "type": 0,
//    "content": "string",
//    "state": 0,
//    "gmtCreate": "string",
//    "gmtModified": "string"

    private Long id;
    private IdUsernameNameOverview customer;
    private Long goodsSkuId;
    private Byte type;
    private String content;
    private Byte state;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime gmtModified;

    public CommentBo(CommentPo po, IdUsernameNameOverview customerOverview) {
        id  = po.getId();
        customer    = customerOverview;
        goodsSkuId  = po.getGoodsSkuId();
        type    = po.getType();
        content = po.getContent();
        state   = po.getState();
        gmtCreate   = po.getGmtCreate();
        gmtModified = po.getGmtModified();
    }
}
