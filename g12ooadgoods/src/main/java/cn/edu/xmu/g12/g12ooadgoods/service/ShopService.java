package cn.edu.xmu.g12.g12ooadgoods.service;

import cn.edu.xmu.g12.g12ooadgoods.dao.ShopDao;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.ShopInfoVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.ShopState;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
}
