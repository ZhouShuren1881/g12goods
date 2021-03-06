package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.mapper.*;
import cn.edu.xmu.g12.g12ooadgoods.model.po.*;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.shop.ShopInfoBo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.ShopState;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.*;

@Repository
public class ShopDao {
    private static final Logger logger = LoggerFactory.getLogger(ShopDao.class);

    @Resource
    ShopPoMapper shopPoMapper;

    public ReturnObject<List<ShopState>> getStates() {
        return new ReturnObject<>(ShopState.getAllStates());
    }

    public ReturnObject<ShopInfoBo> newShop(String name) {
        var shop = new ShopPo();
        shop.setName(name);
        shop.setState((byte)0);
        shop.setGmtCreate(LocalDateTime.now());
        shop.setGmtModified(LocalDateTime.now());

        shopPoMapper.insertSelective(shop);
        // TODO 更新角色DepartId
        return new ReturnObject<>(new ShopInfoBo(shop));
    }

    public ResponseCode modifyShop(Long shopId, String name) {
        var shopPo = shopPoMapper.selectByPrimaryKey(shopId);
        if (shopPo == null) return RESOURCE_ID_NOTEXIST;
        if (shopPo.getState() == 4) return SHOP_STATENOTALLOW;

        var shopSet = new ShopPo();
        shopSet.setId(shopId);
        shopSet.setName(name);
        shopSet.setGmtModified(LocalDateTime.now());
        shopPoMapper.updateByPrimaryKeySelective(shopSet);
        return OK;
    }

    public ResponseCode changeShopState(Long shopId, Byte state) {
        var shopExistPo = shopPoMapper.selectByPrimaryKey(shopId);
        if (shopExistPo == null) return RESOURCE_ID_NOTEXIST;
        var existState = shopExistPo.getState();
        if (existState.equals(state)) return STATE_NOCHANGE;

        logger.info("<changeShopState> exist state="+existState+" aim state="+state);
        if (existState == 3 || existState == 4) return SHOP_STATENOTALLOW;
        if (existState == 0 && state != 1 && state != 4) return SHOP_STATENOTALLOW;
        if (existState == 1 && state != 2 && state != 3) return SHOP_STATENOTALLOW;
        if (existState == 2 && state != 1 && state != 3) return SHOP_STATENOTALLOW;

        var shopPo = new ShopPo();
        shopPo.setId(shopId);
        shopPo.setState(state);
        shopPo.setGmtModified(LocalDateTime.now());
        shopPoMapper.updateByPrimaryKeySelective(shopPo);
        return OK;
    }
}
