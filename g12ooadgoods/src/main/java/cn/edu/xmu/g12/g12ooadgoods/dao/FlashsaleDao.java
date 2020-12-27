package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion.TimeServiceUnion;
import cn.edu.xmu.g12.g12ooadgoods.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.FlashSalePoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.FlashSaleBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.FlashSaleItemBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.ModifyFlashSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleSkuVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FlashsaleDao {
    private static final Logger logger = LoggerFactory.getLogger(FlashsaleDao.class);

    @Autowired
    SkuPriceDao skuPriceDao;

    @Resource
    FlashSalePoMapper flashSalePoMapper;
    @Resource
    FlashSaleItemPoMapper flashSaleItemPoMapper;
    @Resource
    GoodsSkuPoMapper goodsSkuPoMapper;

    @Autowired
    TimeServiceUnion timeServiceUnion;

    /** TODO 响应式API */
    public List<FlashSaleItemBo> getFlashSaleItemInTimeSeg(Long timesegId) {
        var retTimeDTO = timeServiceUnion.getTimeSegmentId(timesegId);
        if (retTimeDTO.getCode() != ResponseCode.OK) return new ArrayList<>();

        var flashsaleExample = new FlashSalePoExample();
        flashsaleExample.createCriteria()
                .andTimeSegIdEqualTo(timesegId)
                .andStateEqualTo((byte)1);
        var flashsaleList = flashSalePoMapper.selectByExample(flashsaleExample);
        if (flashsaleList.size() == 0) return new ArrayList<>();

        var flashsaleIdList = flashsaleList.stream().map(FlashSalePo::getId).collect(Collectors.toList());
        var itemExample = new FlashSaleItemPoExample();
        if (flashsaleIdList.isEmpty())
            itemExample.createCriteria().andIdIsNull(); // 防止空数组出错
        else
            itemExample.createCriteria().andSaleIdIn(flashsaleIdList);
        var flashsaleItemList = flashSaleItemPoMapper.selectByExample(itemExample);

        List<FlashSaleItemBo> flashsaleItemBoList = new ArrayList<>();
        for (var item : flashsaleItemList) {
            var sku = goodsSkuPoMapper.selectByPrimaryKey(item.getGoodsSkuId());
            var skuOverview = new SkuOverview(sku, skuPriceDao.getSkuPrice(sku));
            var itemBo = new FlashSaleItemBo(item, skuOverview);
            flashsaleItemBoList.add(itemBo);
        }
        return flashsaleItemBoList;
    }

    private boolean sameday(LocalDateTime a, LocalDateTime b) {
        return a.getYear() == b.getYear() && a.getDayOfYear() == b.getDayOfYear();
    }

    public ReturnObject<FlashSaleBo> newFlashSale(NewFlashSaleVo vo, Long timesegId) {
        var retTimeDTO = timeServiceUnion.getTimeSegmentId(timesegId);
        if (retTimeDTO.getCode() != ResponseCode.OK) return new ReturnObject<>(retTimeDTO.getCode());

        // 同时间段，未被删除的秒杀
        var flashsaleExample = new FlashSalePoExample();
        flashsaleExample.createCriteria()
                .andTimeSegIdEqualTo(timesegId)
                .andStateNotEqualTo((byte)2);
        var sameTimeSegFlashSaleList = flashSalePoMapper.selectByExample(flashsaleExample);
        var conflictFlashSaleList = sameTimeSegFlashSaleList.stream()
                .filter(item -> sameday(item.getFlashDate(), vo.getFlashDate())).collect(Collectors.toList());
        if (conflictFlashSaleList.size() != 0) return new ReturnObject<>(ResponseCode.TIMESEG_CONFLICT);

        var flashsalePo = new FlashSalePo();
        flashsalePo.setFlashDate(vo.getFlashDate());
        flashsalePo.setTimeSegId(timesegId);
        flashsalePo.setGmtCreate(LocalDateTime.now());
        flashsalePo.setGmtModified(LocalDateTime.now());
        flashsalePo.setState((byte)0);
        flashSalePoMapper.insertSelective(flashsalePo);

        var flashsaleBo = new FlashSaleBo(flashsalePo, timeServiceUnion.getTimeSegOverviewById(timesegId));
        return new ReturnObject<>(flashsaleBo);
    }

    /** TODO 响应式API */
    public List<FlashSaleItemBo> getFlashSaleItemTimeSegNow() {
        var retTimeDTO = timeServiceUnion.getCurrentSegmentId();
        if (retTimeDTO.getCode() != ResponseCode.OK)
            return new ArrayList<>();

        return getFlashSaleItemInTimeSeg(retTimeDTO.getData());
    }

    public ResponseCode chagneFlashSaleState(Long flashsaleId, Byte state) {
        var flashsalePo = flashSalePoMapper.selectByPrimaryKey(flashsaleId);
        if (flashsalePo == null) return ResponseCode.RESOURCE_ID_NOTEXIST;
        if (flashsalePo.getState().equals(state) || flashsalePo.getState() == (byte)2) return ResponseCode.OK;

        var po = new FlashSalePo();
        po.setId(flashsaleId);
        po.setState(state);
        flashSalePoMapper.updateByPrimaryKeySelective(po);
        return ResponseCode.OK;
    }

    public ResponseCode modifyFlashSale(ModifyFlashSaleVo vo, Long flashsaleId) {
        var flashDate = vo.getFlashDate();
        var po = new FlashSalePo();
        po.setId(flashsaleId);
        po.setFlashDate(flashDate);
        int rows = flashSalePoMapper.updateByPrimaryKeySelective(po);
        if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;
        else return ResponseCode.OK;
    }

    public ReturnObject<FlashSaleItemBo> newFlashSaleItem(Long flashsaleId, NewFlashSaleSkuVo vo) {
        var flashsalePo = flashSalePoMapper.selectByPrimaryKey(flashsaleId);
        if (flashsalePo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var skuPo = goodsSkuPoMapper.selectByPrimaryKey(vo.getSkuId());
        if (skuPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        var skuOverview = new SkuOverview(skuPo, skuPriceDao.getSkuPrice(skuPo));

        var flashSaleItemPo = new FlashSaleItemPo();
        flashSaleItemPo.setSaleId(flashsaleId);
        flashSaleItemPo.setGoodsSkuId(vo.getSkuId());
        flashSaleItemPo.setPrice(vo.getPrice());
        flashSaleItemPo.setQuantity(vo.getQuantity());
        flashSaleItemPo.setGmtCreate(LocalDateTime.now());
        flashSaleItemPo.setGmtModified(LocalDateTime.now());
        flashSaleItemPoMapper.insertSelective(flashSaleItemPo);

        return new ReturnObject<>( new FlashSaleItemBo(flashSaleItemPo, skuOverview));
    }

    public ResponseCode deleteFlashSaleItem(Long flashsaleId, Long flashsaleItemId) {
        var flashsaleItemPo = flashSaleItemPoMapper.selectByPrimaryKey(flashsaleItemId);
        if (!flashsaleItemPo.getSaleId().equals(flashsaleId)) return ResponseCode.RESOURCE_ID_NOTEXIST;

        flashSaleItemPoMapper.deleteByPrimaryKey(flashsaleItemId);
        return ResponseCode.OK;
    }
}
