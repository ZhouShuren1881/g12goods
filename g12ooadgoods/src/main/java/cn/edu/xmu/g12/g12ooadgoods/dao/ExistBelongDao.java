package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.controller.GrouponController;
import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ExistBelongDao {
    private static final Logger logger = LoggerFactory.getLogger(ExistBelongDao.class);

    @Resource
    GoodsSpuPoMapper spuPoMapper;
    @Resource
    GoodsSkuPoMapper skuPoMapper;
    @Resource
    FloatPricePoMapper floatPricePoMapper;
    @Resource
    CommentPoMapper commentPoMapper;
    @Resource
    PresaleActivityPoMapper presaleActivityPoMapper;
    @Resource
    GrouponActivityPoMapper grouponActivityPoMapper;
    @Resource
    CouponActivityPoMapper couponActivityPoMapper;

    ResponseCode notexist;
    ResponseCode outscope;
    ResponseCode ok;

    public ExistBelongDao() {
        ok       = ResponseCode.OK;
        notexist = ResponseCode.RESOURCE_ID_NOTEXIST;
        outscope = ResponseCode.RESOURCE_ID_OUTSCOPE;
    }

    /**
     * @param shopId 所制定的shop必须存在
     */
    public ResponseCode spuBelongToShop(Long spuId, Long shopId) {
        if (spuId == null || spuId <= 0 || shopId == null) return notexist;

        var spu = spuPoMapper.selectByPrimaryKey(spuId);
//        var shop = shopPoMapper.selectByPrimaryKey(shopId);
        if (spu == null/* || shop == null*/) return notexist;

        return spu.getShopId().equals(shopId) ? ok : outscope;
    }

    /**
     * @param shopId 所制定的shop可以为0
     */
    public ResponseCode skuBelongToShop(Long skuId, Long shopId) {
        if (skuId == null || skuId <= 0 || shopId == null || shopId < 0) return notexist;

        var sku = skuPoMapper.selectByPrimaryKey(skuId);
        if (sku == null) return notexist;

        var spu = spuPoMapper.selectByPrimaryKey(sku.getGoodsSpuId());
        if (spu == null) return notexist;

        return (spu.getShopId().equals(shopId) || shopId == 0) ? ok : outscope;
    }

    /**
     * @param shopId 所制定的shop必须存在
     */
    public ResponseCode floatPriceBelongToShop(Long floatId, Long shopId) {
        if (floatId == null || floatId <= 0 || shopId == null) return notexist;

        var floatPrice = floatPricePoMapper.selectByPrimaryKey(floatId);
        if (floatPrice == null) return notexist;

        var sku = skuPoMapper.selectByPrimaryKey(floatPrice.getGoodsSkuId());
        if (sku == null) return notexist;

        var spu = spuPoMapper.selectByPrimaryKey(sku.getGoodsSpuId());
//        var shop = shopPoMapper.selectByPrimaryKey(shopId);
        if (spu == null/* || shop == null*/) return notexist;

        return spu.getShopId().equals(shopId) ? ok : outscope;
    }

    /**
     * @param shopId 所制定的shop必须存在
     */
    public ResponseCode commentBelongToShop(Long commentId, Long shopId) {
        if (commentId == null || commentId <= 0 || shopId == null) return notexist;
        var comment = commentPoMapper.selectByPrimaryKey(commentId);
        if (comment == null) return notexist;

        logger.info("Comment id="+comment.getId()+" sku id="+comment.getGoodsSkuId());
        return skuBelongToShop(comment.getGoodsSkuId(), shopId);
    }

    public ResponseCode presaleBelongToShop(Long presaleId, Long shopId) {
        if (presaleId == null || presaleId <= 0 || shopId == null) return notexist;
        var presale = presaleActivityPoMapper.selectByPrimaryKey(presaleId);
        if (presale == null) return notexist;
        return presale.getShopId().equals(shopId) ? ok : outscope;
    }

    public ResponseCode grouponBelongToShop(Long grouponId, Long shopId) {
        if (grouponId == null || grouponId <= 0 || shopId == null) return notexist;
        var groupon = grouponActivityPoMapper.selectByPrimaryKey(grouponId);
        if (groupon == null) return notexist;
        return groupon.getShopId().equals(shopId) ? ok : outscope;
    }

    public ResponseCode couponActBelongToShop(Long couponId, Long shopId) {
        if (couponId == null || couponId <= 0 || shopId == null) return notexist;
        var coupon = couponActivityPoMapper.selectByPrimaryKey(couponId);
        if (coupon == null) return notexist;
        return coupon.getShopId().equals(shopId) ? ok : outscope;
    }
}
