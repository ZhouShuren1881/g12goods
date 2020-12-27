package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.IdNameOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.ListBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.presale.PresaleBo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.ActivityState;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.presale.ModifyPreSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.presale.NewPreSaleVo;
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
import java.util.Objects;

// TODO 预售与团购不同时存在

@Repository
public class PresaleDao {
    private static final Logger logger = LoggerFactory.getLogger(PresaleDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SkuPriceDao skuPriceDao;

    @Resource
    PresaleActivityPoMapper presaleActivityPoMapper;
    @Resource
    ShopPoMapper shopPoMapper;
    @Resource
    GoodsSkuPoMapper goodsSkuPoMapper;

    public ReturnObject<List<ActivityState>> getAllState() {
        return new ReturnObject<>(ActivityState.getAllStates());
    }

    /**
     * Private Method..
     */
    private ReturnObject<ListBo<PresaleBo>> packupPresaleActivityListBo(
            List<PresaleActivityPo> presaleList, Integer page, Integer pageSize) {
        var presaleBoList = new ArrayList<PresaleBo>();
        for (var item : presaleList) {
            var sku = goodsSkuPoMapper.selectByPrimaryKey(item.getGoodsSkuId());
            var shop = shopPoMapper.selectByPrimaryKey(item.getShopId());
            presaleBoList.add(new PresaleBo(
                    item,
                    new SkuOverview(sku, skuPriceDao.getSkuPrice(sku)),
                    new IdNameOverview(shop.getId(), shop.getName())
            ));
        }

        if (page != null) {
            // 返回分页信息
            var pageInfo = new PageInfo<>(presaleBoList);
            return new ReturnObject<>(new ListBo<>(
                    page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), presaleBoList));
        } else
            return new ReturnObject<>(new ListBo<>(
                    1, presaleBoList.size(), (long) presaleBoList.size(), 1, presaleBoList));
    }

    // GET /presales
    public ReturnObject<ListBo<PresaleBo>> getAllValidPresaleActivity(
            Long shopId, Integer timeline, Long skuId, Integer page, Integer pageSize) {
        var presaleExample = new PresaleActivityPoExample();
        var criteria = presaleExample.createCriteria();
        criteria.andStateEqualTo((byte)1);

        if (shopId != null) criteria.andShopIdEqualTo(shopId);
        if (skuId  != null) criteria.andGoodsSkuIdEqualTo(skuId);

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
        var presaleList = presaleActivityPoMapper.selectByExample(presaleExample);

        return packupPresaleActivityListBo(presaleList, page, pageSize);
    }

    // GET /shops/{shopId}/presales
    public ReturnObject<List<PresaleBo>> getSkuAllPresaleActivity(
            Long shopId, @Nullable Long skuId, @Nullable Byte state) {
        var presaleExample = new PresaleActivityPoExample();
        var criteria = presaleExample.createCriteria();

        if (shopId != null) criteria.andShopIdEqualTo(shopId);
        if (skuId  != null) criteria.andGoodsSkuIdEqualTo(skuId);
        criteria.andStateEqualTo(Objects.requireNonNullElseGet(state, () -> (byte) 1));

        var presaleList = presaleActivityPoMapper.selectByExample(presaleExample);

        var presaleBoList = new ArrayList<PresaleBo>();
        for (var item : presaleList) {
            var sku = goodsSkuPoMapper.selectByPrimaryKey(item.getGoodsSkuId());
            var shop = shopPoMapper.selectByPrimaryKey(item.getShopId());
            presaleBoList.add(new PresaleBo(
                    item,
                    new SkuOverview(sku, skuPriceDao.getSkuPrice(sku)),
                    new IdNameOverview(shop.getId(), shop.getName())
            ));
        }

        return new ReturnObject<>(presaleBoList);
    }

    public ReturnObject<PresaleBo> newPresale(Long shopId, Long skuId, NewPreSaleVo vo) {

        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        var skuPo = goodsSkuPoMapper.selectByPrimaryKey(skuId);

        var presaleExample = new PresaleActivityPoExample();
        presaleExample.createCriteria().andGoodsSkuIdEqualTo(skuId);
        var presalePo = presaleActivityPoMapper.selectByExample(presaleExample);
        if (presalePo != null) return new ReturnObject<>(ResponseCode.PRESALE_STATENOTALLOW);

        var newPo = new PresaleActivityPo();
        newPo.setName(vo.getName());
        newPo.setBeginTime(vo.getBeginTime());
        newPo.setPayTime(vo.getPayTime());
        newPo.setEndTime(vo.getEndTime());
        newPo.setState((byte)0);
        newPo.setShopId(shopId);
        newPo.setGoodsSkuId(skuId);
        newPo.setQuantity(vo.getQuantity());
        newPo.setAdvancePayPrice(vo.getAdvancePayPrice());
        newPo.setRestPayPrice(vo.getRestPayPrice());
        newPo.setGmtCreate(LocalDateTime.now());
        newPo.setGmtModified(LocalDateTime.now());

        presaleActivityPoMapper.insert(newPo);
        return new ReturnObject<>(new PresaleBo(
                newPo,
                new SkuOverview(skuPo, skuPriceDao.getSkuPrice(skuPo)),
                new IdNameOverview(shopPo.getId(), shopPo.getName())
        ));
    }

    // TODO 是不是 下线状态才能修改？
    public ResponseCode modifyPresale(Long presaleId, ModifyPreSaleVo vo) {
        var presalePo = presaleActivityPoMapper.selectByPrimaryKey(presaleId);
        if (presalePo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;
        if (presalePo.getState() != 0) return ResponseCode.PRESALE_STATENOTALLOW;

        var updatePo = new PresaleActivityPo();
        updatePo.setId(presaleId);
        updatePo.setName(vo.getName());
        updatePo.setBeginTime(vo.getBeginTime());
        updatePo.setPayTime(vo.getPayTime());
        updatePo.setEndTime(vo.getEndTime());
        updatePo.setQuantity(vo.getQuantity());
        updatePo.setAdvancePayPrice(vo.getAdvancePayPrice());
        updatePo.setRestPayPrice(vo.getRestPayPrice());
        updatePo.setGmtModified(LocalDateTime.now());

        presaleActivityPoMapper.updateByPrimaryKeySelective(updatePo);
        return ResponseCode.OK;
    }

    public ResponseCode changePresaleState(Long presaleId, Byte state) {
        var presalePo = presaleActivityPoMapper.selectByPrimaryKey(presaleId);

        if (state == 2 && presalePo.getState() != 0) return ResponseCode.PRESALE_STATENOTALLOW;
        if (state == 1 && presalePo.getState() != 0) return ResponseCode.PRESALE_STATENOTALLOW;
        if (state == 0 && presalePo.getState() != 1) return ResponseCode.PRESALE_STATENOTALLOW;

        var updatePo = new PresaleActivityPo();
        updatePo.setId(presaleId);
        updatePo.setState(state);
        presaleActivityPoMapper.updateByPrimaryKeySelective(updatePo);
        return ResponseCode.OK;
    }
}
