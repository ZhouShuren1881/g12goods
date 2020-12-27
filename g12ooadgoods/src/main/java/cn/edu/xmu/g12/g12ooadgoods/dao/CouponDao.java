package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion.CustomerServiceUnion;
import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.VoListObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.ListBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.UserOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon.CouponActivityBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon.CouponActivityOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.coupon.CouponOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon.CouponState;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon.ModifyCouponActivityVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon.NewCouponVo;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CouponDao {
    private static final Logger logger = LoggerFactory.getLogger(CouponDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SkuPriceDao skuPriceDao;
    @Autowired
    CustomerServiceUnion customerServiceUnion;

    @Resource
    ShopPoMapper shopPoMapper;
    @Resource
    GoodsSkuPoMapper goodsSkuPoMapper;
    @Resource
    GoodsSpuPoMapper goodsSpuPoMapper;
    @Resource
    CouponActivityPoMapper couponActivityPoMapper;
    @Resource
    CouponPoMapper couponPoMapper;
    @Resource
    CouponSkuPoMapper couponSkuPoMapper;

    public List<CouponState> getAllState() {
        return CouponState.getAllStates();
    }

    public ReturnObject<CouponActivityBo> newCouponActivity(Long shopId, Long userId, NewCouponVo vo) {
        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        var retObjectUserDTO = customerServiceUnion.findCustomerByUserId(userId);
        if (shopPo == null || retObjectUserDTO.getCode() != ResponseCode.OK)
            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
        var userDTO = retObjectUserDTO.getData();

        var couponActPo = new CouponActivityPo();
        couponActPo.setName(vo.getName());
        couponActPo.setBeginTime(vo.getBeginTime());
        couponActPo.setEndTime(vo.getEndTime());
        couponActPo.setCouponTime(vo.getCouponTime());
        couponActPo.setState((byte)0);
        couponActPo.setShopId(shopId);
        couponActPo.setQuantity(vo.getQuantity());
        couponActPo.setValidTerm(vo.getValidTerm());
        couponActPo.setImageUrl(null);
        couponActPo.setStrategy(vo.getStrategy());
        couponActPo.setCreatedBy(userId);
        couponActPo.setModiBy(userId);
        couponActPo.setGmtCreate(LocalDateTime.now());
        couponActPo.setGmtModified(LocalDateTime.now());
        couponActPo.setQuantitiyType(vo.getQuantityType());

        couponActivityPoMapper.insert(couponActPo);
        return new ReturnObject<>( new CouponActivityBo(
                couponActPo,
                new IdNameOverview(shopPo.getId(), shopPo.getName()),
                new UserOverview(userId, userDTO),
                new UserOverview(userId, userDTO)
        ));
    }

    // TODO 上传照片
    public ResponseCode uploadCouponImg() {
        return ResponseCode.OK;
    }

    /**
     * Private Method..
     */
    private ListBo<CouponActivityOverview> packupCouponActivityListBo(
            List<CouponActivityPo> couponList, Integer page, Integer pageSize) {

        var couponActivityOverviewList = couponList.stream()
                .map(CouponActivityOverview::new).collect(Collectors.toList());

        if (page != null) {
            // 返回分页信息
            var pageInfo = new PageInfo<>(couponList);
            return new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), couponActivityOverviewList);
        } else
            return new ListBo<>(1, couponActivityOverviewList.size(), (long) couponActivityOverviewList.size(), 1, couponActivityOverviewList);
    }

    // GET /couponactivities
    public ListBo<CouponActivityOverview> getAllCoupon(@Nullable Long shopId, @Nullable Integer timeline,
                                                 @Nullable Integer page, @Nullable Integer pageSize) {

        var couponActExample = new CouponActivityPoExample();
        var criteria = couponActExample.createCriteria();
        criteria.andStateEqualTo((byte)1);

        if (shopId != null) criteria.andShopIdEqualTo(shopId);

        if (timeline != null) {
            // timeline : 0 还未开始的， 1 明天开始的，2 正在进行中的，3 已经结束的
            var now = LocalDateTime.now();
            switch (timeline) {
                case 0:
                    criteria.andBeginTimeGreaterThan(now);
                    break;
                case 1:
                    var nextday = now.plusDays(1);
                    var nextZero = LocalDateTime.of(
                            now.getYear(), now.getMonth(), now.getDayOfMonth(),0,0);
                    var doubleNextZero = LocalDateTime.of(
                            nextday.getYear(), nextday.getMonth(), nextday.getDayOfMonth(),0,0);
                    criteria.andBeginTimeGreaterThanOrEqualTo(nextZero);
                    criteria.andBeginTimeLessThan(doubleNextZero);
                    break;
                case 2:
                    criteria.andBeginTimeLessThan(now);
                    criteria.andEndTimeGreaterThan(now);
                    break;
                default:
                    criteria.andEndTimeLessThan(now);
            }
        }

        if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var cuoponActList = couponActivityPoMapper.selectByExample(couponActExample);

        return packupCouponActivityListBo(cuoponActList, page, pageSize);
    }

    // GET /shops/{shopId}/couponactivities/invalid
    public ListBo<CouponActivityOverview> getOffShelveCoupon(Long shopId,
                                                        @Nullable Integer page, @Nullable Integer pageSize) {

        var couponActExample = new CouponActivityPoExample();
        couponActExample.createCriteria()
                .andStateEqualTo((byte)0)
                .andShopIdEqualTo(shopId);

        if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var cuoponActList = couponActivityPoMapper.selectByExample(couponActExample);

        return packupCouponActivityListBo(cuoponActList, page, pageSize);
    }

    // GET /couponactivities/{couponActId}/skus
    public ListBo<SkuOverview> getSkuInCouponAct(Long couponActId,
                                                 @Nullable Integer page, @Nullable Integer pageSize) {
        var couponSkuExample = new CouponSkuPoExample();
        couponSkuExample.createCriteria().andActivityIdEqualTo(couponActId);

        if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var couponSkuList = couponSkuPoMapper.selectByExample(couponSkuExample);
        var skuIdList = couponSkuList.stream().map(CouponSkuPo::getSkuId).collect(Collectors.toList());

        var skuExample = new GoodsSkuPoExample();
        if (skuIdList.isEmpty())
            skuExample.createCriteria().andIdIsNull(); // 防止空数组出错
        else
            skuExample.createCriteria().andIdIn(skuIdList);
        var skuPoList = goodsSkuPoMapper.selectByExample(skuExample);
        var skuOverviewList
                = skuPoList.stream()
                .map(item -> new SkuOverview(item, item.getOriginalPrice()))
                .collect(Collectors.toList());

        if (page != null) {
            // 返回分页信息
            var pageInfo = new PageInfo<>(couponSkuList);
            return new ListBo<>(
                    page,
                    pageSize,
                    pageInfo.getTotal(),
                    pageInfo.getPages(),
                    skuOverviewList);
        } else
            return new ListBo<>(
                    1,
                    skuOverviewList.size(),
                    (long) skuOverviewList.size(),
                    1,
                    skuOverviewList);
    }

    public ReturnObject<CouponActivityBo> getCouponActivityDetail(Long shopId, Long couponActId) {
        var shop = shopPoMapper.selectByPrimaryKey(shopId);
        if (shop == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var couponAct = couponActivityPoMapper.selectByPrimaryKey(couponActId);
        if (couponAct == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        shop = shopPoMapper.selectByPrimaryKey(couponAct.getShopId());

        var roCreateUserDTO
                = customerServiceUnion.findCustomerByUserId(couponAct.getCreatedBy());
        if (roCreateUserDTO.getCode() != ResponseCode.OK)
            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
        var createUserDTO = roCreateUserDTO.getData();

        var roModifyUserDTO
                = customerServiceUnion.findCustomerByUserId(couponAct.getCreatedBy());
        if (roModifyUserDTO.getCode() != ResponseCode.OK)
            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
        var modifyUserDTO = roModifyUserDTO.getData();

        return new ReturnObject<>(new CouponActivityBo(
                couponAct,
                new IdNameOverview(shop.getId(), shop.getName()),
                new UserOverview(couponAct.getCreatedBy(), createUserDTO),
                new UserOverview(couponAct.getModiBy(), modifyUserDTO)
        ));
    }

    public ResponseCode modifyCouponActivity(Long shopId, Long couponActId, ModifyCouponActivityVo vo) {
        var shop = shopPoMapper.selectByPrimaryKey(shopId);
        if (shop == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        var couponAct = couponActivityPoMapper.selectByPrimaryKey(couponActId);
        if (couponAct == null) return ResponseCode.RESOURCE_ID_NOTEXIST;
        if (couponAct.getState() != 0) return ResponseCode.COUPON_STATENOTALLOW;

        couponAct = new CouponActivityPo();
        couponAct.setName(vo.getName());
        couponAct.setQuantity(vo.getQuantity());
        couponAct.setBeginTime(vo.getBeginTime());
        couponAct.setEndTime(vo.getEnd_time());
        couponAct.setStrategy(vo.getStrategy());

        return ResponseCode.OK;
    }

    public ResponseCode changeCouponActivityState(Long shopId, Long couponActId, Byte state) {
        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        if (shopPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        var couponActPo = couponActivityPoMapper.selectByPrimaryKey(couponActId);
        if (couponActPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        if (state == 2 && couponActPo.getState() != 0) return ResponseCode.COUPON_STATENOTALLOW;
        if (state == 1 && couponActPo.getState() != 0) return ResponseCode.COUPON_STATENOTALLOW;
        if (state == 0 && couponActPo.getState() != 1) return ResponseCode.COUPON_STATENOTALLOW;

        var updatePo = new CouponActivityPo();
        updatePo.setId(couponActId);
        updatePo.setState(state);
        couponActivityPoMapper.updateByPrimaryKeySelective(updatePo);
        return ResponseCode.OK;
    }

    public ResponseCode addSkuListIntoCouponSku(Long shopId, Long couponActId, List<Long> skuIdList) {
        var skuPoList = new ArrayList<GoodsSkuPo>();
        for (var item : skuIdList) {
            var skuPo = goodsSkuPoMapper.selectByPrimaryKey(item);
            if (skuPo == null || skuPo.getState() == 2) return ResponseCode.RESOURCE_ID_NOTEXIST;
            var spuPo = goodsSpuPoMapper.selectByPrimaryKey(skuPo.getGoodsSpuId());
            if (spuPo == null || !spuPo.getShopId().equals(shopId))
                return ResponseCode.RESOURCE_ID_OUTSCOPE;
            skuPoList.add(skuPo);
        }
        for (var item : skuPoList) {
            var couponSkuPo = new CouponSkuPo();
            couponSkuPo.setActivityId(couponActId);
            couponSkuPo.setSkuId(item.getId());
            couponSkuPo.setGmtCreate(LocalDateTime.now());
            couponSkuPo.setGmtModified(LocalDateTime.now());
            couponSkuPoMapper.insert(couponSkuPo);
        }
        return ResponseCode.OK;
    }

    public ResponseCode deleteSkuListFromCouponSku(Long shopId, Long couponActId, List<Long> skuIdList) {
        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        if (shopPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        var couponActPo = couponActivityPoMapper.selectByPrimaryKey(couponActId);
        if (couponActPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

        var couponSkuIdList = new ArrayList<Long>();
        for (var item : skuIdList) {
            var couponSkuPo = couponSkuPoMapper.selectByPrimaryKey(item);
            if (couponSkuPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;
            var skuPo = goodsSkuPoMapper.selectByPrimaryKey(item);
            if (skuPo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;
            var spuPo = goodsSpuPoMapper.selectByPrimaryKey(skuPo.getGoodsSpuId());
            if (spuPo == null || !spuPo.getShopId().equals(shopId))
                return ResponseCode.RESOURCE_ID_OUTSCOPE;
            couponSkuIdList.add(item);
        }

        for (var item : couponSkuIdList) couponSkuPoMapper.deleteByPrimaryKey(item);
        return ResponseCode.OK;
    }

    public ListBo<CouponOverview> getMyCouponList(Long userId, @Nullable Byte state,
                                                  @Nullable Integer page, @Nullable Integer pageSize) {
        var couponExample = new CouponPoExample();
        var criteria = couponExample.createCriteria();
        criteria.andCustomerIdEqualTo(userId);
        if (state != null) criteria.andStateEqualTo(state);

        if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
        var couponList = couponPoMapper.selectByExample(couponExample);

        var couponOvList = new ArrayList<CouponOverview>();
        for (var item : couponList) {
            var activityPo = couponActivityPoMapper.selectByPrimaryKey(item.getActivityId());
            couponOvList.add(new CouponOverview(item, activityPo));
        }

        if (page != null) {
            // 返回分页信息
            var pageInfo = new PageInfo<>(couponList);
            return new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), couponOvList);
        } else
            return new ListBo<>( 1, couponOvList.size(), (long) couponOvList.size(), 1, couponOvList);
    }

    public ReturnObject<VoListObject<String>> customerGetCoupon(Long userId, Long couponActId) {
        var couponActPo = couponActivityPoMapper.selectByPrimaryKey(couponActId);
        if (couponActPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var now = LocalDateTime.now();
        if (now.isBefore(couponActPo.getCouponTime())) return new ReturnObject<>(ResponseCode.COUPON_NOTBEGIN);
        if (now.isAfter(couponActPo.getEndTime())) return new ReturnObject<>(ResponseCode.COUPON_END);
        if (couponActPo.getQuantitiyType() == 1 && couponActPo.getQuantity() <= 0)
            return new ReturnObject<>(ResponseCode.COUPON_FINISH);

        var snList = new VoListObject<String>();
        var snNum = 0;

        //判断优惠活动的quantityType为0，且用户无此优惠卷，生成优惠卷的数目为quantity
        //判断优惠活动的quantityType为1，且用户无此优惠卷，去从优惠卷中领一张优惠卷
        if (couponActPo.getQuantitiyType() == 1) {
            var updateCouponActivityPo = new CouponActivityPo();
            updateCouponActivityPo.setId(couponActPo.getId());
            updateCouponActivityPo.setQuantity(couponActPo.getQuantity()-1);
            couponActivityPoMapper.updateByPrimaryKeySelective(updateCouponActivityPo);
            snNum = 1;
        } else {
            snNum = couponActPo.getQuantity();
        }
        for (int i = 0 ; i < snNum; i++) {
            String sn = null;
            while (sn == null) {
                sn = UUID.randomUUID().toString();
                var couponExample = new CouponPoExample();
                couponExample.createCriteria().andCouponSnEqualTo(sn);
                var sameSnPo = couponPoMapper.selectByExample(couponExample);
                if (sameSnPo != null) sn = null;
            }

            var newCoupon = new CouponPo();
            newCoupon.setCouponSn(sn);
            newCoupon.setName(couponActPo.getName());
            newCoupon.setCustomerId(userId);
            newCoupon.setActivityId(couponActPo.getId());
            newCoupon.setBeginTime(couponActPo.getBeginTime());
            newCoupon.setEndTime(couponActPo.getEndTime());
            newCoupon.setState((byte)1);
            newCoupon.setGmtCreate(LocalDateTime.now());
            newCoupon.setGmtModified(LocalDateTime.now());
            couponPoMapper.insert(newCoupon);

            snList.add(sn);
        }
        return new ReturnObject<>(snList);
    }
}
