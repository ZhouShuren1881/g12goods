package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.GoodsSkuPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsSkuPo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SkuPriceDao {
    private static final Logger logger = LoggerFactory.getLogger(SkuPriceDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired(required = false)
    GoodsSkuPoMapper goodsSkuPoMapper;

    public Long getSkuPrice(Long skuId) {
        var sku = goodsSkuPoMapper.selectByPrimaryKey(skuId);
        return sku.getOriginalPrice();
    }

    public Long getSkuPrice(GoodsSkuPo sku) {
        return sku.getOriginalPrice();
    }

}
