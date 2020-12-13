package cn.edu.xmu.g12.g12ooadgoods.model.bo;

import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private Long customerId;
    private Long goodsSkuId;
    private Long orderitemId;
    private Byte type;
    private String content;
    private Byte state;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getGoodsSkuId() {
        return goodsSkuId;
    }
    public void setGoodsSkuId(Long goodsSkuId) {
        this.goodsSkuId = goodsSkuId;
    }
    public Long getOrderitemId() {
        return orderitemId;
    }
    public void setOrderitemId(Long orderitemId) {
        this.orderitemId = orderitemId;
    }
    public Byte getType() {
        return type;
    }
    public void setType(Byte type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    public Byte getState() {
        return state;
    }
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 构造函数     *
     * @param po 用PO构造
     * @return Comment
     */
    public Comment(CommentPo po) {
        this.id = po.getId();
        this.customerId=po.getCustomerId();
        this.goodsSkuId=po.getGoodsSkuId();
        this.orderitemId=po.getOrderitemId();
        this.type=po.getType();
        this.content=po.getContent();
        this.state = po.getState();
    }

    /**
     * 用bo对象创建用于更新的po对象
     * @return CommentPo
     */
    public CommentPo getOrderPo() {
        CommentPo po = new CommentPo();
        po.setId(this.getId());
        po.setCustomerId(this.getCustomerId());
        po.setGoodsSkuId(this.getGoodsSkuId());
        po.setOrderitemId(this.getOrderitemId());
        po.setType(this.getType());
        po.setContent(this.getContent());
        po.setState(this.getState());
        return po;
    }

}