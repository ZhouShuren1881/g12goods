package cn.edu.xmu.g12.g12ooadgoods.service;

import cn.edu.xmu.g12.g12ooadgoods.dao.GoodDao;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuListDetailBo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GoodService {
    @Autowired
    GoodDao good;

    public Object getStates() {
        return ResponseUtil.ok(GoodState.getAllStates());
    }

    public ReturnObject<VoObject> getSkuList( Long shopId, String skuSn, Long spuId,
                                              String spuSn, Integer page, Integer pageSize) {
        if ((page==null) != (pageSize==null)
                || page!=null && page.intValue()< 0
                || pageSize!=null && pageSize.intValue() <= 0)
            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
        SkuListDetailBo skuListDetailBo = null;
        if (shopId != null) skuListDetailBo = good.getSkuListByShopId(shopId, page, pageSize);
        if (skuSn != null) skuListDetailBo = good.getSkuListBySpuSn(skuSn, page, pageSize);
        if (spuId != null) skuListDetailBo = good.getSkuListBySpuId(spuId, page, pageSize);
        if (spuSn != null) skuListDetailBo = good.getSkuListBySpuSn(spuSn, page, pageSize);
        return new ReturnObject<>(skuListDetailBo);
    }

    public ReturnObject<VoObject> getSkuById(Long skuId) {
        var result = good.getSkuById(skuId);
        if (result == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        return new ReturnObject<>(result);
    }
}
