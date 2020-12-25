package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ExistBelongDao {
    @Autowired(required = false)
    GoodsSpuPoMapper spuPoMapper;
    @Autowired(required = false)
    GoodsSkuPoMapper skuPoMapper;
    @Autowired(required = false)
    BrandPoMapper brandPoMapper;
    @Autowired(required = false)
    GoodsCategoryPoMapper categoryPoMapper;
    @Autowired(required = false)
    ShopPoMapper shopPoMapper;
    @Autowired(required = false)
    SkuPriceDao skuPriceDao;
    @Autowired(required = false)
    FloatPricePoMapper floatPricePoMapper;
    @Autowired(required = false)
    CommentPoMapper commentPoMapper;
    @Autowired(required = false)
    PresaleActivityPoMapper presaleActivityPoMapper;
    @Autowired(required = false)
    GrouponActivityPoMapper grouponActivityPoMapper;
    @Autowired(required = false)
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
     * @param shopId 所制定的shop必须存在
     */
    public ResponseCode skuBelongToShop(Long skuId, Long shopId) {
        if (skuId == null || skuId <= 0 || shopId == null) return notexist;

        var sku = skuPoMapper.selectByPrimaryKey(skuId);
        if (sku == null) return notexist;

        var spu = spuPoMapper.selectByPrimaryKey(sku.getGoodsSpuId());
//        var shop = shopPoMapper.selectByPrimaryKey(shopId);
        if (spu == null/* || shop == null*/) return notexist;

        return spu.getShopId().equals(shopId) ? ok : outscope;
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
        var groupon = couponActivityPoMapper.selectByPrimaryKey(couponId);
        if (groupon == null) return notexist;
        return groupon.getShopId().equals(shopId) ? ok : outscope;
    }
}
