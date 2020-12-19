package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.controller.GoodController;
import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.*;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.github.pagehelper.PageHelper;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GoodDao {

    private static final Logger logger = LoggerFactory.getLogger(GoodDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    public List<GoodsSkuPo> getSkuListBySkuSn(String skuSn) {
        var session = sqlSessionFactory.openSession();
        var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
        var goodsSkuExample = new GoodsSkuPoExample();
        goodsSkuExample.createCriteria().andSkuSnEqualTo(skuSn);
        var goodsSkuList = goodsSkuMapper.selectByExample(goodsSkuExample);
        session.commit();
        session.close();
        return goodsSkuList;
    }

    /**
     *
     * @param spuId
     * @param page 和 pageSize 同时为null 或者 同时不空.由Service层过滤
     * @return
     */
    public SkuListDetailBo getSkuListBySpuId(Long spuId, Integer page, Integer pageSize) {
        // TODO 处理价格
        var session = sqlSessionFactory.openSession();
        try {
            // 获取Sku信息
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
            var goodsSkuExample = new GoodsSkuPoExample();
            goodsSkuExample.createCriteria().andGoodsSpuIdEqualTo(spuId);
            if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
            var goodsSkuPoList =  goodsSkuMapper.selectByExample(goodsSkuExample);

            // 获取Sku现在价格
            var skuOverviewList = new ArrayList<SkuOverview>();
            for (var item : goodsSkuPoList) skuOverviewList.add( new SkuOverview(item, item.getOriginalPrice()) );

            // 返回分页信息
            var pageInfo = new PageInfo<>(goodsSkuPoList);
            if (page != null)
                return new SkuListDetailBo(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), skuOverviewList);
            else
                return new SkuListDetailBo(1, skuOverviewList.size(), (long) skuOverviewList.size(), 1, skuOverviewList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public SkuListDetailBo getSkuListBySpuSn(String spuSn, Integer page, Integer pageSize) {
        // TODO 处理价格
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSpuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var goodsSpuExample = new GoodsSpuPoExample();
            goodsSpuExample.createCriteria().andGoodsSnEqualTo(spuSn);
            var goodsSpuList = goodsSpuMapper.selectByExample(goodsSpuExample);
            var spuId = goodsSpuList.get(0).getId();

            // 获取Sku信息
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
            var goodsSkuExample = new GoodsSkuPoExample();
            goodsSkuExample.createCriteria().andGoodsSpuIdEqualTo(spuId);
            if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
            var goodsSkuPoList = goodsSkuMapper.selectByExample(goodsSkuExample);

            // 获取Sku现在价格
            var skuOverviewList = new ArrayList<SkuOverview>();
            for (var item : goodsSkuPoList) skuOverviewList.add( new SkuOverview(item, item.getOriginalPrice()) );

            // 返回分页信息
            var pageInfo = new PageInfo<>(goodsSkuPoList);
            if (page != null)
                return new SkuListDetailBo(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), skuOverviewList);
            else
                return new SkuListDetailBo(1, skuOverviewList.size(), (long) skuOverviewList.size(), 1, skuOverviewList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public SkuListDetailBo getSkuListByShopId(Long shopId, Integer page, Integer pageSize) {
        // TODO 处理价格
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSpuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var goodsSpuExample = new GoodsSpuPoExample();
            goodsSpuExample.createCriteria().andShopIdEqualTo(shopId);
            var goodsSpuList = goodsSpuMapper.selectByExample(goodsSpuExample);

            var spuIdList = new ArrayList<Long>();
            for (var item : goodsSpuList) spuIdList.add(item.getId());

            // 获取Sku信息
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
            var goodsSkuExample = new GoodsSkuPoExample();
            goodsSkuExample.createCriteria().andGoodsSpuIdIn(spuIdList);
            if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
            var goodsSkuPoList = goodsSkuMapper.selectByExample(goodsSkuExample);

            // 获取Sku现在价格
            var skuOverviewList = new ArrayList<SkuOverview>();
            for (var item : goodsSkuPoList) skuOverviewList.add( new SkuOverview(item, item.getOriginalPrice()) );

            // 返回分页信息
            var pageInfo = new PageInfo<>(goodsSkuPoList);
            if (page != null)
                return new SkuListDetailBo(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), skuOverviewList);
            else
                return new SkuListDetailBo(1, skuOverviewList.size(), (long) skuOverviewList.size(), 1, skuOverviewList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public SkuDetailBo getSkuById(Long skuId) {
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSpuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
            var brandMapper = session.getMapper(BrandPoMapper.class);
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);
            var freightModelMapper = session.getMapper(FreightModelPoMapper.class);
            var shopMapper = session.getMapper(ShopPoMapper.class);

            var targetSku = goodsSkuMapper.selectByPrimaryKey(skuId);
            if (targetSku == null) return null;

            var spu = goodsSpuMapper.selectByPrimaryKey(targetSku.getGoodsSpuId());
            if (spu == null) return null;

            var brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
            var category = categoryMapper.selectByPrimaryKey(spu.getCategoryId());
            var freightModel = freightModelMapper.selectByPrimaryKey(spu.getFreightId());
            var shop = shopMapper.selectByPrimaryKey(spu.getShopId());
            var spec = (SpuSpecSubOverview)JSON.parse(spu.getSpec());

            var goodsSkuExample = new GoodsSkuPoExample();
            goodsSkuExample.createCriteria().andGoodsSpuIdEqualTo(spu.getId());
            var skuList = goodsSkuMapper.selectByExample(goodsSkuExample);

            // TODO Fix the Good real PRICE.
            var skuOverviewList = new ArrayList<SkuOverview>();
            for (var item : skuList) skuOverviewList.add(new SkuOverview(item, item.getOriginalPrice()));

            // TODO Fix the Good real PRICE.
            var targetSkuPrice = targetSku.getOriginalPrice();

            // TODO Fix the Good Shareable
            Boolean targetSkuShareable = true;

            var spuOverview = new SpuOverview(spu, brand, category, freightModel, shop, spec, skuOverviewList);
            return new SkuDetailBo(targetSku, targetSkuPrice, targetSkuShareable, spuOverview);
        } finally {
            session.commit();
            session.close();
        }
    }

}
