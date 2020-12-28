package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion.CustomerServiceUnion;
import cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion.OrderServiceUnion;
import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.ListBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.comment.CommentBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.comment.IdUsernameNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon.CouponActivityOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.comment.CommentState;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.comment.ConfirmCommentVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.comment.NewCommentVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon.CouponState;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CommentDao {
    private static final Logger logger = LoggerFactory.getLogger(CouponDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SkuPriceDao skuPriceDao;

    @Resource
    GoodsSkuPoMapper goodsSkuPoMapper;
    @Resource
    GoodsSpuPoMapper goodsSpuPoMapper;
    @Resource
    CommentPoMapper commentPoMapper;

    @Autowired
    OrderServiceUnion orderServiceUnion;
    @Autowired
    CustomerServiceUnion customerServiceUnion;

    public ReturnObject<List<CommentState>> getAllStates() {
        return new ReturnObject<>(CommentState.getAllStates());
    }

    public ReturnObject<CommentBo> newSkuComment(Long orderItemId, Long userId, NewCommentVo vo) {
        var returnOrderDTO = orderServiceUnion.getUserSelectSOrderInfo(userId, orderItemId);
        if (returnOrderDTO.getCode() != ResponseCode.OK) return new ReturnObject<>(returnOrderDTO.getCode());
        var orderDTO = returnOrderDTO.getData();

        var commentPo = new CommentPo();
        commentPo.setCustomerId(userId);
        commentPo.setGoodsSkuId(orderDTO.getSkuId());
        commentPo.setType(vo.getType());
        commentPo.setContent(vo.getContent());
        commentPo.setState((byte)0);
        commentPo.setGmtCreate(LocalDateTime.now());
        commentPo.setGmtModified(LocalDateTime.now());
        commentPoMapper.insertSelective(commentPo);

        var returnCustomerDTO = customerServiceUnion.findCustomerByUserId(userId);
        var customerDTO = returnCustomerDTO.getData(); /* 经过权限模块校验,必定命中 */
        return new ReturnObject<>(new CommentBo(commentPo, new IdUsernameNameOverview(userId, customerDTO)));
    }

    /**
     * Private Method..
     */
    private ListBo<CommentBo> packupCommentListBo(
            List<CommentPo> commentPoList, @NotNull Integer page, @NotNull Integer pageSize) {

        var commentBoList = commentPoList.stream()
                .map(item-> new CommentBo(
                        item,
                        new IdUsernameNameOverview(item.getCustomerId(),
                                customerServiceUnion.findCustomerByUserId(item.getCustomerId()).getData()
                        )
                )).collect(Collectors.toList());

        // 返回分页信息
        var pageInfo = new PageInfo<>(commentPoList);
        return new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), commentBoList);
    }

    public ReturnObject<ListBo<CommentBo>> getSkuCommentValid(
            Long skuId, @NotNull  Integer page, @NotNull  Integer pageSize) {

        var skuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        if (skuPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var commentExample = new CommentPoExample();
        commentExample.createCriteria().andGoodsSkuIdEqualTo(skuId);
        PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var commentPoList = commentPoMapper.selectByExample(commentExample);

        return new ReturnObject<>(packupCommentListBo(commentPoList, page, pageSize));
    }

    /**
     *
     * @param shopId PathVariable JWT校验过，无需验证, shopId=0管理员
     */
    public ResponseCode confirmComment(Long shopId, Long commentId, ConfirmCommentVo vo) {
        var commentPo = commentPoMapper.selectByPrimaryKey(commentId);

        var returnOrderDTO
                = orderServiceUnion.getShopSelectOrderInfo(shopId, commentPo.getOrderitemId());
        if (returnOrderDTO.getCode() != ResponseCode.OK) return returnOrderDTO.getCode();

        var updatePo = new CommentPo();
        updatePo.setId(commentId);
        updatePo.setState(vo.getConclusion() ? (byte)1 : (byte)2);
        updatePo.setGmtModified(LocalDateTime.now());
        commentPoMapper.updateByPrimaryKeySelective(updatePo);
        return ResponseCode.OK;
    }

    /**
     *
     * @param userId JWT,无需Dao验证
     */
    public ReturnObject<ListBo<CommentBo>> getCommentOfUser(
            Long userId, @NotNull  Integer page, @NotNull Integer pageSize) {
        var returnCustomerDTO = customerServiceUnion.findCustomerByUserId(userId);
        var customerDTO = returnCustomerDTO.getData(); /* 必中，userId经过了校验 */

        var commentExample = new CommentPoExample();
        commentExample.createCriteria().andCustomerIdEqualTo(userId);
        PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var commentPoList = commentPoMapper.selectByExample(commentExample);

        var commentBoList = commentPoList.stream().map(item->
                new CommentBo(item, new IdUsernameNameOverview(userId, customerDTO))).collect(Collectors.toList());

        // 返回分页信息
        var pageInfo = new PageInfo<>(commentPoList);
        return new ReturnObject<>(
                new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), commentBoList));
    }

    /**
     * /shops/{id}/comments/all 管理员查看评论列表
     *
     * @param shopId PathVariable [带访问权限-Controller验证] shopId可以为零，只有管理员可以这样
     * @param state BodyVariable [state大小验证-Controller]
     */
    public ReturnObject<ListBo<CommentBo>> getShopCommentByAdmin(
            Long shopId, @Nullable Byte state,
            @NotNull Integer page, @NotNull Integer pageSize) {
        var commentExample = new CommentPoExample();
        var criteria = commentExample.createCriteria();

        if (shopId > 0) {
            var spuExample = new GoodsSpuPoExample();
            spuExample.createCriteria().andShopIdEqualTo(shopId);
            var spuList = goodsSpuPoMapper.selectByExample(spuExample);
            var spuIdList = spuList.stream().map(GoodsSpuPo::getId).collect(Collectors.toList());
            if (spuIdList.isEmpty()) {
                criteria.andIdIsNull(); // 防止空数组出错
            } else {
                var skuExample = new GoodsSkuPoExample();
                skuExample.createCriteria().andGoodsSpuIdIn(spuIdList);
                var skuPoList = goodsSkuPoMapper.selectByExample(skuExample);
                var skuIdList = skuPoList.stream().map(GoodsSkuPo::getId).collect(Collectors.toList());
                if (skuIdList.isEmpty())
                    criteria.andIdIsNull(); // 防止空数组出错
                else
                    criteria.andGoodsSkuIdIn(skuIdList);
            }
        }

        if (state != null) criteria.andStateEqualTo(state);
        if (!(shopId > 0 && state != null)) criteria.andIdIsNotNull(); // 选择所有

        PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var commentPoList = commentPoMapper.selectByExample(commentExample);
        return new ReturnObject<>(packupCommentListBo(commentPoList, page, pageSize));
    }
}
