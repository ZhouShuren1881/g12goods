package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.*;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.github.pagehelper.PageHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class GoodDao {

    private static final Logger logger = LoggerFactory.getLogger(GoodDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    ObjectMapper jsonMapper = new ObjectMapper();

    static class SQLFailException extends Exception {
        SQLFailException() {
            super("SQL执行失败!");
        }
    }

    static class NotExistException extends Exception {
        NotExistException() {
            super("不存在!");
        }
    }

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
    public SkuListBo getSkuListBySpuId(Long spuId, Integer page, Integer pageSize) {
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
                return new SkuListBo(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), skuOverviewList);
            else
                return new SkuListBo(1, skuOverviewList.size(), (long) skuOverviewList.size(), 1, skuOverviewList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public SkuListBo getSkuListBySpuSn(String spuSn, Integer page, Integer pageSize) {
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
                return new SkuListBo(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), skuOverviewList);
            else
                return new SkuListBo(1, skuOverviewList.size(), (long) skuOverviewList.size(), 1, skuOverviewList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public SkuListBo getSkuListByShopId(Long shopId, Integer page, Integer pageSize) {
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
                return new SkuListBo(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), skuOverviewList);
            else
                return new SkuListBo(1, skuOverviewList.size(), (long) skuOverviewList.size(), 1, skuOverviewList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public SkuBo getSkuById(Long skuId) {
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);

            var targetSku = goodsSkuMapper.selectByPrimaryKey(skuId);
            if (targetSku == null) return null;

            // TODO Fix the Good real PRICE.
            var targetSkuPrice = targetSku.getOriginalPrice();

            // TODO Fix the Good Sku Detail Shareable
            Boolean targetSkuShareable = true;

            var spuOverview = getSpu(session, targetSku.getGoodsSpuId());
            var skuBo = new SkuBo(targetSku, targetSkuPrice, targetSkuShareable, spuOverview);
            session.commit();
            return skuBo;
        } catch (NotExistException e) {
            session.rollback();
            return null;
        } finally {
            session.close();
        }
    }

    public ReturnObject<SkuOverview> newSku(Long shopId, Long spuId, NewSkuVo vo) {
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSpuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);

            var goodsSkuExample = new GoodsSkuPoExample();
            var confictList = goodsSkuMapper.selectByExample(goodsSkuExample);
            if (confictList.size()!=0) return new ReturnObject<>(ResponseCode.SKUSN_SAME);

            var goodsSpu = goodsSpuMapper.selectByPrimaryKey(spuId);
            if (goodsSpu == null || !goodsSpu.getShopId().equals(shopId))
                return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

            var newSkuPo = new GoodsSkuPo();
            newSkuPo.setGoodsSpuId(spuId);
            newSkuPo.setName(vo.getName());
            newSkuPo.setOriginalPrice(vo.getOriginalPrice());
            newSkuPo.setConfiguration(vo.getConfiguration());
            newSkuPo.setWeight(vo.getWeight());
            newSkuPo.setImageUrl(vo.getImageUrl());
            newSkuPo.setInventory(vo.getInventory());
            newSkuPo.setDetail(vo.getDetail());
            newSkuPo.setDisabled((byte)0);
            newSkuPo.setGmtCreate(LocalDateTime.now());
            newSkuPo.setGmtModified(LocalDateTime.now());
            newSkuPo.setState((byte)0);

            int rows = goodsSkuMapper.insert(newSkuPo);
            if (rows == 0) throw new SQLFailException();

            session.commit();
            // TODO Fix the Good real PRICE.
            return new ReturnObject<>(new SkuOverview(newSkuPo, newSkuPo.getOriginalPrice()));
        } catch (SQLFailException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        } finally {
            session.close();
        }
    }

    public ResponseCode changeSkuState(Long skuId, Byte state) {
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
            var skuPo = new GoodsSkuPo();
            skuPo.setId(skuId);
            skuPo.setState(state);

            int rows = goodsSkuMapper.updateByPrimaryKey(skuPo);
            if (rows == 0) throw new SQLFailException();

            session.commit();
            return ResponseCode.OK;
        } catch (SQLFailException e) {
            session.rollback();
            return ResponseCode.RESOURCE_ID_NOTEXIST;
        } finally {
            session.close();
        }
    }

    public ResponseCode modifySku(Long skuId, ModifySkuVo vo) {
        var session = sqlSessionFactory.openSession();
        try {
            var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);
            var skuPo = vo.convertToGoodsSkuPo();
            skuPo.setId(skuId);
            skuPo.setGmtModified(LocalDateTime.now());

            int rows = goodsSkuMapper.updateByPrimaryKey(skuPo);
            if (rows == 0) throw new SQLFailException();

            session.commit();
            return ResponseCode.OK;
        } catch (SQLFailException e) {
            session.rollback();
            return ResponseCode.RESOURCE_ID_NOTEXIST;
        } finally {
            session.close();
        }
    }

    public ReturnObject<List<GoodsCategoryPo>> getSubCategory(Long pid) {
        var session = sqlSessionFactory.openSession();
        try {
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);
            var categoryPo = categoryMapper.selectByPrimaryKey(pid);
            if (categoryPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

            var categoryExample = new GoodsCategoryPoExample();
            categoryExample.createCriteria().andPidEqualTo(pid);
            var subCategoryPoList = categoryMapper.selectByExample(categoryExample);
            return new ReturnObject<>(subCategoryPoList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public ReturnObject<CategoryBo> newCategory(Long pid, String name) {
        var session = sqlSessionFactory.openSession();
        try {
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);
            var categoryParent = categoryMapper.selectByPrimaryKey(pid);
            if (categoryParent == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

            var categoryPo = new GoodsCategoryPo();
            categoryPo.setName(name);
            categoryPo.setPid(pid);
            categoryPo.setGmtCreate(LocalDateTime.now());
            categoryPo.setGmtModified(LocalDateTime.now());
            int rows = categoryMapper.insert(categoryPo);
            if (rows == 0) throw new SQLFailException();

            session.commit();
            return new ReturnObject<>(new CategoryBo(categoryPo));
        } catch (SQLFailException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.INTERNAL_SERVER_ERR);
        }  finally {
            session.close();
        }
    }

    public ResponseCode modifyCategory(Long categoryId, String name) {
        var session = sqlSessionFactory.openSession();
        try {
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);

            var categoryPo = new GoodsCategoryPo();
            categoryPo.setId(categoryId);
            categoryPo.setName(name);
            int rows = categoryMapper.updateByPrimaryKey(categoryPo);
            if (rows == 0) throw new SQLFailException();

            session.commit();
            return ResponseCode.OK;
        } catch (SQLFailException e) {
            session.rollback();
            return ResponseCode.RESOURCE_ID_NOTEXIST;
        }  finally {
            session.close();
        }
    }

    public ResponseCode deleteCategory(Long categoryId) {
        // 将子类目的pid设置为被删除的类目的pid
        // 将spu中的类目设置为类目的pid
        var session = sqlSessionFactory.openSession();
        try {
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);

            var targetCategory = categoryMapper.selectByPrimaryKey(categoryId);
            if (targetCategory == null) throw new SQLFailException();

            var childCategoryExample = new GoodsCategoryPoExample();
            var childSpuExample = new GoodsSpuPoExample();
            var childCategoryPo = new GoodsCategoryPo();
            var childSpuPo = new GoodsSpuPo();
            childCategoryExample.createCriteria().andPidEqualTo(targetCategory.getId());
            childSpuExample.createCriteria().andCategoryIdEqualTo(targetCategory.getId());
            childCategoryPo.setPid(targetCategory.getPid());
            childSpuPo.setCategoryId(targetCategory.getPid());

            categoryMapper.updateByExampleSelective(childCategoryPo, childCategoryExample);
            spuMapper.updateByExampleSelective(childSpuPo, childSpuExample);
            categoryMapper.deleteByPrimaryKey(categoryId);

            session.commit();
            return ResponseCode.OK;
        } catch (SQLFailException e) {
            session.rollback();
            return ResponseCode.RESOURCE_ID_NOTEXIST;
        }  finally {
            session.close();
        }
    }

    public SpuOverview getSpuById(Long spuId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spu = getSpu(session, spuId);
            return spu;
        } catch (NotExistException e) {
            session.rollback();
            return null;
        } finally {
            session.commit();
            session.close();
        }
    }

    private SpuOverview getSpu(SqlSession session, Long spuId) throws NotExistException {
        var goodsSpuMapper = session.getMapper(GoodsSpuPoMapper.class);
        var brandMapper = session.getMapper(BrandPoMapper.class);
        var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);
        var freightModelMapper = session.getMapper(FreightModelPoMapper.class);
        var shopMapper = session.getMapper(ShopPoMapper.class);
        var goodsSkuMapper = session.getMapper(GoodsSkuPoMapper.class);

        var spu = goodsSpuMapper.selectByPrimaryKey(spuId);
        if (spu == null) return null;

        var brand = brandMapper.selectByPrimaryKey(spu.getBrandId());
        var category = categoryMapper.selectByPrimaryKey(spu.getCategoryId());
        var freightModel = freightModelMapper.selectByPrimaryKey(spu.getFreightId());
        var shop = shopMapper.selectByPrimaryKey(spu.getShopId());
        SpecOverview spec;
        try {
            spec = jsonMapper.readValue(spu.getSpec(), SpecOverview.class);
        } catch (JsonProcessingException e) {
            spec = null;
        }

        var goodsSkuExample = new GoodsSkuPoExample();
        goodsSkuExample.createCriteria().andGoodsSpuIdEqualTo(spu.getId());
        var skuList = goodsSkuMapper.selectByExample(goodsSkuExample);

        // TODO Fix the Good real PRICE.
        var skuOverviewList = new ArrayList<SkuOverview>();
        for (var item : skuList) skuOverviewList.add(new SkuOverview(item, item.getOriginalPrice()));

        return new SpuOverview(spu, brand, category, freightModel, shop, spec, skuOverviewList);
    }

    public ReturnObject<SpuOverview> newSpu(NewSpuVo vo, Long shopId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);

            String uuid = "";
            while (true) {
                String tempuuid = UUID.randomUUID().toString();
                var spuExample = new GoodsSpuPoExample();
                spuExample.createCriteria().andGoodsSnEqualTo(tempuuid);
                var sameSn = spuMapper.selectByExample(spuExample);
                if (sameSn == null) break;
            }

            var spuPo = new GoodsSpuPo();
            spuPo.setName(vo.getName());
            spuPo.setBrandId(0L);
            spuPo.setCategoryId(0L);
            spuPo.setFreightId(0L);
            spuPo.setShopId(shopId);
            spuPo.setGoodsSn(uuid);
            spuPo.setDetail(vo.getDecription());
            spuPo.setImageUrl("");
            spuPo.setSpec(vo.getSpecs());
            spuPo.setDisabled((byte)0);
            spuPo.setGmtCreate(LocalDateTime.now());
            spuPo.setGmtModified(LocalDateTime.now());

            spuMapper.insert(spuPo);
            var spu = getSpu(session, spuPo.getId());
            return new ReturnObject<>(spu);
        } catch (NotExistException e) {
            session.rollback();
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        } finally {
            session.commit();
            session.close();
        }
    }

    // todo ------------------ split line --------------------
    // todo ------------------ split line --------------------
    // todo ------------------ split line --------------------
    // todo ------------------ split line --------------------
    // todo ------------------ split line --------------------
    // todo ------------------ split line --------------------
    // todo ------------------ split line --------------------

    public ResponseCode modifySpu(ModifySpuVo vo, Long spuId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);

            var spuPo = new GoodsSpuPo();
            spuPo.setId(spuId);
            spuPo.setName(vo.getName());
            spuPo.setDetail(vo.getDecription());
            spuPo.setSpec(vo.getSpecs());
            spuPo.setGmtModified(LocalDateTime.now());

            spuMapper.updateByPrimaryKey(spuPo);
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode deleteSpu(Long spuId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);

            var spuPo = new GoodsSpuPo();
            spuPo.setId(spuId);
            spuPo.setDisabled((byte)1);
            spuPo.setGmtModified(LocalDateTime.now());

            spuMapper.updateByPrimaryKey(spuPo);
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    // Sku onshelves -> change sku state

    // Sku offshelve -> change sku state

    public ReturnObject<FloatPriceBo> newFloatPrice(NewFloatPriceVo vo, Long skuId, Long userId) {
        var session = sqlSessionFactory.openSession();
        try {
            var floatPriceMapper = session.getMapper(FloatPricePoMapper.class);

            var floatPriceExample = new FloatPricePoExample();
            floatPriceExample.createCriteria().andGoodsSkuIdEqualTo(skuId);
            var floatPriceList = floatPriceMapper.selectByExample(floatPriceExample);

            var conflictList = floatPriceList.stream().filter(item ->
                    item.getValid() == 1 && (
                            item.getBeginTime().isAfter(vo.getBeginTime())
                            && item.getBeginTime().isBefore(vo.getEndTime())
                            || item.getEndTime().isAfter(vo.getBeginTime())
                            && item.getEndTime().isBefore(vo.getEndTime())
                    )).collect(Collectors.toList());
            if (conflictList.size() != 0) return new ReturnObject<>(ResponseCode.SKUPRICE_CONFLICT);

            var floatPricePo = new FloatPricePo();
            floatPricePo.setGoodsSkuId(skuId);
            floatPricePo.setActivityPrice(vo.getActivityPrice());
            floatPricePo.setBeginTime(vo.getBeginTime());
            floatPricePo.setEndTime(vo.getEndTime());
            floatPricePo.setQuantity(vo.getQuantity());
            floatPricePo.setCreatedBy(userId);
            floatPricePo.setInvalidBy(0L);
            floatPricePo.setValid((byte)1);
            floatPricePo.setGmtCreate(LocalDateTime.now());
            floatPricePo.setGmtModified(LocalDateTime.now());
            floatPriceMapper.insert(floatPricePo);

            var UserMapper = session.getMapper(AuthUserPoMapper.class);
            var userPo = UserMapper.selectByPrimaryKey(userId);
            var userOverview = new UserOverview(userPo);

            var floatPriceBo = new FloatPriceBo(floatPricePo, userOverview, null);

            return new ReturnObject<>(floatPriceBo);
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode endisableFloatPrice(Long floatPriceId, Long userId) {
        var session = sqlSessionFactory.openSession();
        try {
            var floatPriceMapper = session.getMapper(FloatPricePoMapper.class);

            var floatPricePo = new FloatPricePo();
            floatPricePo.setId(floatPriceId);
            floatPricePo.setInvalidBy(userId);
            floatPricePo.setValid((byte)0);
            floatPricePo.setGmtModified(LocalDateTime.now());
            floatPriceMapper.updateByPrimaryKey(floatPricePo);

            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ReturnObject<BrandBo> newBrand(NewBrandVo vo, Long shopId) {
        var session = sqlSessionFactory.openSession();
        try {
            var brandMapper = session.getMapper(BrandPoMapper.class);

            var brandPo = new BrandPo();
            brandPo.setName(vo.getName());
            brandPo.setDetail(vo.getDetail());
            brandPo.setImageUrl("");
            brandPo.setGmtCreate(LocalDateTime.now());
            brandPo.setGmtCreate(LocalDateTime.now());
            brandMapper.insert(brandPo);

            return new ReturnObject<>(new BrandBo(brandPo));
        } finally {
            session.commit();
            session.close();
        }
    }

    public ListBo<BrandBo> getAllBrands(Integer page, Integer pageSize) {
        var session = sqlSessionFactory.openSession();
        try {
            var brandMapper = session.getMapper(BrandPoMapper.class);

            var brandPoExample = new BrandPoExample();
            if (page != null) PageHelper.startPage(page, pageSize); // 设置整个线程的Page选项
            brandPoExample.createCriteria().andGmtCreateNotEqualTo(null);
            var brandPoList = brandMapper.selectByExample(brandPoExample);

            List<BrandBo> brandBoList = new ArrayList<>();
            for (var item : brandPoList) brandBoList.add(new BrandBo(item));

            // 返回分页信息
            var pageInfo = new PageInfo<>(brandPoList);
            if (page != null)
                return new ListBo<>(page, pageSize, pageInfo.getTotal(), pageInfo.getPages(), brandBoList);
            else
                return new ListBo<>(1, brandPoList.size(), (long) brandPoList.size(), 1, brandBoList);
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode modifyBrand(ModifyBrandVo vo, Long brandId) {
        var session = sqlSessionFactory.openSession();
        try {
            var brandMapper = session.getMapper(BrandPoMapper.class);

            var brandPo = new BrandPo();
            brandPo.setId(brandId);
            brandPo.setName(vo.getName());
            brandPo.setDetail(vo.getDetail());
            brandPo.setGmtModified(LocalDateTime.now());

            brandMapper.updateByPrimaryKey(brandPo);
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode deleteBrand(Long brandId) {
        var session = sqlSessionFactory.openSession();
        try {
            var brandMapper = session.getMapper(BrandPoMapper.class);
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);

            int rows = brandMapper.deleteByPrimaryKey(brandId);
            if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var spuExample = new GoodsSpuPoExample();
            var spuPo = new GoodsSpuPo();
            spuExample.createCriteria().andBrandIdEqualTo(brandId);
            spuPo.setBrandId(0L);
            spuMapper.updateByExample(spuPo, spuExample);
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode addSpuIntoCategory(Long spuId, Long categoryId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);

            var category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var spuPo = new GoodsSpuPo();
            spuPo.setId(spuId);
            spuPo.setCategoryId(categoryId);
            int rows = spuMapper.updateByPrimaryKey(spuPo);
            if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode removeSpuFromCategory(Long spuId, Long categoryId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var categoryMapper = session.getMapper(GoodsCategoryPoMapper.class);

            var category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var spuPo = new GoodsSpuPo();
            spuPo.setId(spuId);
            spuPo.setCategoryId(0L);
            int rows = spuMapper.updateByPrimaryKey(spuPo);
            if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode addSpuIntoBrand(Long spuId, Long brandId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var brandMapper = session.getMapper(BrandPoMapper.class);

            var category = brandMapper.selectByPrimaryKey(brandId);
            if (category == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var spuPo = new GoodsSpuPo();
            spuPo.setId(spuId);
            spuPo.setBrandId(brandId);
            int rows = spuMapper.updateByPrimaryKey(spuPo);
            if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }

    public ResponseCode removeSpuFromBrand(Long spuId, Long brandId) {
        var session = sqlSessionFactory.openSession();
        try {
            var spuMapper = session.getMapper(GoodsSpuPoMapper.class);
            var brandMapper = session.getMapper(BrandPoMapper.class);

            var category = brandMapper.selectByPrimaryKey(brandId);
            if (category == null) return ResponseCode.RESOURCE_ID_NOTEXIST;

            var spuPo = new GoodsSpuPo();
            spuPo.setId(spuId);
            spuPo.setBrandId(0L);
            int rows = spuMapper.updateByPrimaryKey(spuPo);
            if (rows == 0) return ResponseCode.RESOURCE_ID_NOTEXIST;
            return ResponseCode.OK;
        } finally {
            session.commit();
            session.close();
        }
    }
}
