package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.FlashSaleItemPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.FlashSalePoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.TimeSegmentPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.FlashSaleBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.FlashSaleItemBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.TimeSeqOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleSkuVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FlashsaleDao {
    private final byte FlashSaleType = 1;

    private static final Logger logger = LoggerFactory.getLogger(FlashsaleDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SkuPriceDao skuPriceDao;

    ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired(required = false)
    FlashSalePoMapper flashSalePoMapper;
    @Autowired(required = false)
    FlashSaleItemPoMapper flashSaleItemPoMapper;
    @Autowired(required = false)
    TimeSegmentPoMapper timeSegmentPoMapper;
    @Autowired(required = false)
    GoodsSkuPoMapper goodsSkuPoMapper;

    public List<FlashSaleItemBo> getFlashSaleItemInTimeSeg(Long timesegId) {
        var flashsaleExample = new FlashSalePoExample();
        flashsaleExample.createCriteria().andTimeSegIdEqualTo(timesegId);
        var flashsaleList = flashSalePoMapper.selectByExample(flashsaleExample);
        if (flashsaleList.size() == 0) return new ArrayList<>();

        var flashsaleIdList = flashsaleList.stream().map(FlashSalePo::getId).collect(Collectors.toList());
        var itemExample = new FlashSaleItemPoExample();
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
        var timesegPo = timeSegmentPoMapper.selectByPrimaryKey(timesegId);
        if (timesegPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var flashsaleExample = new FlashSalePoExample();
        flashsaleExample.createCriteria().andTimeSegIdEqualTo(timesegId);
        // 同时间段，未被删除的秒杀
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
        flashSalePoMapper.insert(flashsalePo);

        var flashsaleBo = new FlashSaleBo(flashsalePo, new TimeSeqOverview(timesegPo));
        return new ReturnObject<>(flashsaleBo);
    }

    public List<FlashSaleItemBo> getFlashSaleItemTimeSegNow() {
        var now = LocalDateTime.now();
        // 偷懒吧！一共才几个时间段...
        var timesegExample = new TimeSegmentPoExample();
        timesegExample.createCriteria().andBeginTimeIsNotNull().andTypeEqualTo(FlashSaleType);
        var timesegList = timeSegmentPoMapper.selectByExample(timesegExample);
        var thisTimeSegList = timesegList.stream().filter(
                item ->
                !item.getBeginTime().isAfter(now) && !item.getEndTime().isBefore(now)
        ).collect(Collectors.toList());
        if (thisTimeSegList.size() == 0) return new ArrayList<>();

        // 确定时间段Id
        var timesegId = thisTimeSegList.get(0).getId();

        return getFlashSaleItemInTimeSeg(timesegId);
    }

    public ResponseCode chagneFlashSaleState(Long flashsaleId, Byte state) {
        var po = new FlashSalePo();
        po.setId(flashsaleId);
        po.setState(state);
        int rows = flashSalePoMapper.updateByPrimaryKey(po);
        if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;
        else return ResponseCode.OK;
    }

    public ResponseCode modifyFlashSale(Long flashsaleId, LocalDateTime flashDate) {
        var po = new FlashSalePo();
        po.setId(flashsaleId);
        po.setFlashDate(flashDate);
        int rows = flashSalePoMapper.updateByPrimaryKey(po);
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
        flashSaleItemPoMapper.insert(flashSaleItemPo);

        return new ReturnObject<>( new FlashSaleItemBo(flashSaleItemPo, skuOverview));
    }

    public ResponseCode deleteFlashSaleItem(Long flashsaleId, Long flashsaleItemId) {
        var flashsaleItemPo = flashSaleItemPoMapper.selectByPrimaryKey(flashsaleItemId);
        if (!flashsaleItemPo.getSaleId().equals(flashsaleId)) return ResponseCode.RESOURCE_ID_NOTEXIST;

        flashSaleItemPoMapper.deleteByPrimaryKey(flashsaleItemId);
        return ResponseCode.OK;
    }
}
