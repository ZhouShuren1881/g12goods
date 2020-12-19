package cn.edu.xmu.g12.g12ooadgoods.service;

import cn.edu.xmu.g12.g12ooadgoods.dao.ShopDao;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    ShopDao shopDao;

    public Object getStates() {
        return ResponseUtil.ok(ShopState.getAllStates());
    }

    public ReturnObject<VoObject> createShop(Long userId, Long departId, String name) {
        if (departId != 0) return new ReturnObject<>(ResponseCode.USER_HASSHOP);

        var returnShopPo = shopDao.createShop(userId, name);
        if (returnShopPo.getCode() == ResponseCode.OK) {
            var shopInfoVo = new ShopInfoVo();
            shopInfoVo.setId(returnShopPo.getData().getId());
            shopInfoVo.setName(returnShopPo.getData().getName());
            shopInfoVo.setState(returnShopPo.getData().getState());
            shopInfoVo.setGmtCreate(returnShopPo.getData().getGmtCreate());
            shopInfoVo.setGmtModified(returnShopPo.getData().getGmtModified());
            return new ReturnObject<>(shopInfoVo);
        } else {
            return new ReturnObject<>(returnShopPo.getCode());
        }
    }

    public ReturnObject<VoObject> modifyShop(Long departId, String name) {
        var responceCode = shopDao.modifyShop(departId, name);
        return new ReturnObject<>(responceCode);
    }

    public ReturnObject<VoObject> deleteShop(Long shopId) {
        var responceCode = shopDao.changeShopState(shopId, (byte)3);
        return new ReturnObject<>(responceCode);
    }

    public ReturnObject<VoObject> auditShop(Long shopId, Boolean audit) {
        var responceCode = shopDao.changeShopState(shopId, (audit?(byte)1:(byte)4));
        return new ReturnObject<>(responceCode);
    }

    public ReturnObject<VoObject> shopOnshelves(Long shopId) {
        var responceCode = shopDao.changeShopState(shopId, (byte)2);
        return new ReturnObject<>(responceCode);
    }

    public ReturnObject<VoObject> shopOffshelves(Long shopId) {
        var responceCode = shopDao.changeShopState(shopId, (byte)1);
        return new ReturnObject<>(responceCode);
    }
}
