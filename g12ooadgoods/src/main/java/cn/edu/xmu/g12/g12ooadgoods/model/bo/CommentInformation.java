package cn.edu.xmu.g12.g12ooadgoods.model.bo;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.CommentInfoRetVo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentInformation implements VoObject, Serializable {
    private Long id;
    private Long customerId;
    private Long goodsSkuId;
    private Long orderitemId;
    private Byte type;
    private String content;
    private Byte state;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    /**
     * 构造函数     *
     * @param po 用PO构造
     * @return Comment
     */
    public CommentInformation(CommentPo po) {
        if(po.getId()!=null)
            this.id = po.getId();
        if(po.getCustomerId()!=null)
            this.customerId=po.getCustomerId();
        if(po.getGoodsSkuId()!=null)
            this.goodsSkuId=po.getGoodsSkuId();
        if(po.getOrderitemId()!=null)
            this.orderitemId=po.getOrderitemId();
        if(po.getType()!=null)
            this.type=po.getType();
        if(po.getContent()!=null)
            this.content=po.getContent();
        if(po.getState()!=null)
            this.state = po.getState();
        if(po.getGmtCreate()!=null)
            this.gmtCreate=po.getGmtCreate();
        if(po.getGmtModified()!=null)
            this.gmtModified=po.getGmtModified();
    }

    public Boolean authetic(){
        return true;
    }

    @Override
    public Object createVo() {
        return new CommentInfoRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
