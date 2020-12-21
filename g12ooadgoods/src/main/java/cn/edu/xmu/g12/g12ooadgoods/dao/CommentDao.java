package cn.edu.xmu.g12.g12ooadgoods.dao;

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

    ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired(required = false)
    ShopPoMapper shopPoMapper;
    @Autowired(required = false)
    GoodsSkuPoMapper goodsSkuPoMapper;
    @Autowired(required = false)
    GoodsSpuPoMapper goodsSpuPoMapper;
    @Autowired(required = false)
    CouponActivityPoMapper couponActivityPoMapper;
    @Autowired(required = false)
    CouponPoMapper couponPoMapper;
    @Autowired(required = false)
    CouponSkuPoMapper couponSkuPoMapper;
    @Autowired(required = false)
    AuthUserPoMapper authUserPoMapper;
    @Autowired(required = false)
    OrderItemPoMapper orderItemPoMapper;
    @Autowired(required = false)
    OrdersPoMapper ordersPoMapper;
    @Autowired(required = false)
    CommentPoMapper commentPoMapper;

    public List<CommentState> getAllState() {
        return CommentState.getAllStates();
    }

    public ReturnObject<CommentBo> NewSkuComment(Long orderId, Long userId, NewCommentVo vo) {
        var orderItemPo = orderItemPoMapper.selectByPrimaryKey(orderId);
        if (orderItemPo == null) return new ReturnObject<>(ResponseCode.USER_NOTBUY);

        var ordersPo = ordersPoMapper.selectByPrimaryKey(orderItemPo.getOrderId());
        if (!ordersPo.getCustomerId().equals(userId)) return new ReturnObject<>(ResponseCode.USER_NOTBUY);

        var commentPo = new CommentPo();
        commentPo.setCustomerId(ordersPo.getCustomerId());
        commentPo.setGoodsSkuId(orderItemPo.getGoodsSkuId());
        commentPo.setType(vo.getType());
        commentPo.setContent(vo.getContent());
        commentPo.setState((byte)0);
        commentPo.setGmtCreate(LocalDateTime.now());
        commentPo.setGmtModified(LocalDateTime.now());

        commentPoMapper.insert(commentPo);
        var userPo = authUserPoMapper.selectByPrimaryKey(userId);
        return new ReturnObject<>(new CommentBo(commentPo, new IdUsernameNameOverview(userPo)));
    }

    /**
     * Private Method..
     */
    private ListBo<CommentBo> packupCommentListBo(
            List<CommentPo> commentPoList, Integer page, Integer pageSize) {

        var commentBoList = commentPoList.stream()
                .map(item-> new CommentBo(
                        item,
                        new IdUsernameNameOverview(authUserPoMapper.selectByPrimaryKey(item.getCustomerId()))
                )).collect(Collectors.toList());

        // 返回分页信息
        var pageInfo = new PageInfo<>(commentPoList);
        if (page != null)
            return new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), commentBoList);
        else
            return new ListBo<>(1, commentBoList.size(), (long) commentBoList.size(), 1, commentBoList);
    }

    public ReturnObject<ListBo<CommentBo>> getSkuCommentValid(Long skuId,
                                                @Nullable Integer page, @Nullable Integer pageSize) {

        var skuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        if (skuPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var commentExample = new CommentPoExample();
        commentExample.createCriteria().andGoodsSkuIdEqualTo(skuId);
        if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var commentPoList = commentPoMapper.selectByExample(commentExample);

        return new ReturnObject<>(packupCommentListBo(commentPoList, page, pageSize));
    }

    public ResponseCode confirmComment(Long shopId, Long commentId, ConfirmCommentVo vo) {
        var commentPo = commentPoMapper.selectByPrimaryKey(commentId);
        if (commentPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;
        var orderitemPo = orderItemPoMapper.selectByPrimaryKey(commentPo.getOrderitemId());
        var ordersPo = ordersPoMapper.selectByPrimaryKey(orderitemPo.getOrderId());
        if (!ordersPo.getShopId().equals(shopId)) return ResponseCode.RESOURCE_ID_OUTSCOPE;

        var updatePo = new CommentPo();
        updatePo.setId(commentId);
        updatePo.setState(vo.getConclusion() ? (byte)1 : (byte)2);
        updatePo.setGmtModified(LocalDateTime.now());
        commentPoMapper.updateByPrimaryKey(updatePo);
        return ResponseCode.OK;
    }

    public ReturnObject<ListBo<CommentBo>> getSkuCommentByAdmin(Long skuId, Long shopId, @Nullable Byte state,
                                                              @Nullable Integer page, @Nullable Integer pageSize) {

        // TODO 手动实现的评论分页

        var skuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        if (skuPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var commentExample = new CommentPoExample();
        var criteria = commentExample.createCriteria();
        criteria.andGoodsSkuIdEqualTo(skuId);
        if (state != null) criteria.andStateEqualTo(state);

        var commentPoList = commentPoMapper.selectByExample(commentExample);
        if (page == null || pageSize == null) {
            return new ReturnObject<>(packupCommentListBo(commentPoList, null, null));
        } else {
            List<CommentBo> splitCommentList = new ArrayList<>();
            int sindex = (page - 1) * pageSize;
            int eindex = page * pageSize;
            for (int i = sindex; i < eindex && i < commentPoList.size(); i++) {
                var commentBo = new CommentBo(
                        commentPoList.get(i),
                        new IdUsernameNameOverview(
                                authUserPoMapper.selectByPrimaryKey(commentPoList.get(i).getCustomerId()) )
                );
                splitCommentList.add(commentBo);
            }
            int _pagesize = pageSize == 0 ? commentPoList.size() : pageSize;
            int pages = commentPoList.size() / _pagesize;
            long totalSize = commentPoList.size();
            return new ReturnObject<>(new ListBo<>(page, pageSize, totalSize, pages, splitCommentList));
        }
    }
}
