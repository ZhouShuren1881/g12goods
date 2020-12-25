package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.FloatPricePoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.GoodsSpuPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FloatPricePo;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FloatPricePoExample;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Repository
public class SkuPriceDao {
    private static final Logger logger = LoggerFactory.getLogger(SkuPriceDao.class);

    @Resource
    FloatPricePoMapper floatPricePoMapper;
    @Resource
    GoodsSkuPoMapper skuPoMapper;

    public Long getSkuPrice(Long skuId) {
        var sku = skuPoMapper.selectByPrimaryKey(skuId);
        return getSkuPrice(sku);
    }

    public Long getSkuPrice(GoodsSkuPo sku) {
        var now = LocalDateTime.now();
        var priceExample = new FloatPricePoExample();
        priceExample.createCriteria()
                .andGoodsSkuIdEqualTo(sku.getId())
                .andBeginTimeLessThanOrEqualTo(now)
                .andEndTimeGreaterThanOrEqualTo(now)
                .andQuantityGreaterThan(0)
                .andValidEqualTo((byte)1);
        var priceList = floatPricePoMapper.selectByExample(priceExample);
        if (priceList.size() == 0) return sku.getOriginalPrice();
        return priceList.get(0).getActivityPrice();
    }

}
