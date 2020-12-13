package cn.edu.xmu.g12.g12ooadgoods.model.vo;

import cn.edu.xmu.g12.g12ooadgoods.model.bo.CommentInformation;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "评论返回视图")
public class CommentInfoRetVo {
    @ApiModelProperty(value="评论id")
    private Long id;
    @ApiModelProperty(value="顾客id")
    private Long customerId;
    @ApiModelProperty(value="Sku id")
    private Long goodsSkuId;
    @ApiModelProperty(value="评价")
    private Byte type;
    @ApiModelProperty(value="评论内容")
    private String content;
    @ApiModelProperty(value="审核状态")
    private Byte state;
    @ApiModelProperty(value="评论创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value="评论修改时间")
    private LocalDateTime gmtModified;


    public CommentInfoRetVo(CommentInformation bo) {
        if(bo.getId()!=null)
            this.id = bo.getId();
        if(bo.getCustomerId()!=null)
            this.customerId=bo.getCustomerId();
        if(bo.getGoodsSkuId()!=null)
            this.goodsSkuId=bo.getGoodsSkuId();
        if(bo.getType()!=null)
            this.type=bo.getType();
        if(bo.getContent()!=null)
            this.content=bo.getContent();
        if(bo.getState()!=null)
            this.state = bo.getState();
        if(bo.getGmtCreate()!=null)
            this.gmtCreate=bo.getGmtCreate();
        if(bo.getGmtModified()!=null)
            this.gmtModified=bo.getGmtModified();
    }
}
