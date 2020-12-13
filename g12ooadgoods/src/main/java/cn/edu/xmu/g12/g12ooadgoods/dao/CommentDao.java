package cn.edu.xmu.g12.g12ooadgoods.dao;


import cn.edu.xmu.g12.g12ooadgoods.mapper.CommentPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.ShopPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import cn.edu.xmu.g12.g12ooadgoods.util.CommentStatus;
import cn.edu.xmu.g12.g12ooadgoods.util.JacksonUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class CommentDao{

    private static final Logger logger = LoggerFactory.getLogger(CommentDao.class);

    @Autowired
    private CommentPoMapper commentPoMapper;

    @Autowired
    private ShopPoMapper shopPoMapper;

    /**
     * 管理员审核评论
     * createdBy TGF 2020/12/13 20：50
     * @param id 评论id
     * @param state 操作字段（是否通过审核）
     * @return Object 操作信息
     */
    public ReturnObject updateState(Long id, Object state){
        ReturnObject retObj = null;
        try{
            CommentPo commentPo = commentPoMapper.selectByPrimaryKey(id);
            if (commentPo == null) {
                //修改失败
                logger.debug("Confirm Comment State failed: orderId '" + id + "' not exist!");
                retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
            } else {
                if(commentPo.getState() == CommentStatus.UN_CONFIRMED.getCode()){
                    Map messageContent= JacksonUtil.toMap(state.toString());
                    commentPo.setState((Byte)messageContent.get("message"));
                    commentPo.setGmtModified(LocalDateTime.now());
                    int result = commentPoMapper.updateByPrimaryKeySelective(commentPo);
                    if(result == 1){
                        // 审核成功
                        logger.debug("Confirm Comment State Succeed: commentId '" + id + "'");
                        retObj = new ReturnObject(ResponseCode.OK, "成功");
                    }
                    else{
                        // 审核失败
                        logger.debug("Confirm Comment State failed: orderId '" + id + "' has been confirmed!");
                        retObj = new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
                    }
                }
                else{
                    // 该评论已被审核，审核失败
                    logger.debug("Update Order Message failed: '" + id + "' order Status Not Allowed!");
                    retObj = new ReturnObject(ResponseCode.ORDER_STATENOTALLOW, "订单状态禁止");
                }
            }
        }
        catch (DataAccessException e) {
            //数据库错误
            logger.debug("other sql exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("数据库错误：%s", e.getMessage()));
        }
        catch (Exception e) {
            // 其他Exception错误
            logger.error("other exception : " + e.getMessage());
            retObj = new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR, String.format("发生了严重的数据库错误：%s", e.getMessage()));
        }
        return retObj;
    }

    /**
     * 买家查询自己的评价记录
     * @author TGF
     * @created 2020/12/13 21：25
     * @param customerId 买家id
     * @return ReturnObject<CommentsVo> 查询结果
     */
    public PageInfo<CommentPo> selectMyComments(Long customerId){
        PageInfo<CommentPo> retObj = null;
        try{
            logger.debug("Select My Comments with customerId: " + customerId);
            CommentPoExample example = new CommentPoExample();
            CommentPoExample.Criteria criteria = example.createCriteria();
            if(customerId != null){
                criteria.andCustomerIdEqualTo(customerId);
                criteria.andBeDeletedIsNull();
                List<CommentPo> commentPos = commentPoMapper.selectByExample(example);
                logger.debug("Order Information Size: " + commentPos.size());
                retObj = new PageInfo<>(commentPos);
            }
            else{
                logger.debug("Select My Comment Failed: customerId " + customerId + " is null!");
            }
        }
        catch (DataAccessException e) {
            //数据库错误
            logger.debug("Sql exception : " + e.getMessage());
        }
        catch (Exception e) {
            // 其他Exception错误
            logger.error("System exception : " + e.getMessage());
        }
        return retObj;
    }

}
