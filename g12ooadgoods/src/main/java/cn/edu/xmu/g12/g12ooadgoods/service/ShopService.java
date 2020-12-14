package cn.edu.xmu.g12.g12ooadgoods.service;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.ShopState;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Service
public class ShopService {


    public Object getStates() {
        return ResponseUtil.ok(ShopState.getAllStates());
    }

    public ReturnObject<VoObject> createShop(Long userID, String name) {


        return null; // TODO
    }
}
