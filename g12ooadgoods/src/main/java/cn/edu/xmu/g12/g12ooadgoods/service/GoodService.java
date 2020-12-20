package cn.edu.xmu.g12.g12ooadgoods.service;

import cn.edu.xmu.g12.g12ooadgoods.dao.GoodDao;
import cn.edu.xmu.g12.g12ooadgoods.model.VoListObject;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuListBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SpecOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.GoodsCategoryPo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.*;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodService {
    @Autowired
    GoodDao good;

    ObjectMapper jsonMapper = new ObjectMapper();

    public Object getStates() {
        return ResponseUtil.ok(GoodState.getAllStates());
    }

    public ReturnObject<VoObject> getSkuList( Long shopId, String skuSn, Long spuId,
                                              String spuSn, Integer page, Integer pageSize) {
        if ((page==null) != (pageSize==null)
                || page!=null && page.intValue()< 0
                || pageSize!=null && pageSize.intValue() <= 0)
            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
        SkuListBo skuListBo = null;
        if (shopId != null) skuListBo = good.getSkuListByShopId(shopId, page, pageSize);
        if (skuSn != null) skuListBo = good.getSkuListBySpuSn(skuSn, page, pageSize);
        if (spuId != null) skuListBo = good.getSkuListBySpuId(spuId, page, pageSize);
        if (spuSn != null) skuListBo = good.getSkuListBySpuSn(spuSn, page, pageSize);
        return new ReturnObject<>(skuListBo);
    }

    public ReturnObject<VoObject> getSkuById(Long skuId) {
        var result = good.getSkuById(skuId);
        if (result == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        return new ReturnObject<>(result);
    }

    public ReturnObject<VoObject> newSku(Long shopId, Long spuId, NewSkuVo vo) {
        var returnObject = good.newSku(shopId, spuId, vo);
        if (returnObject.getCode() == ResponseCode.OK)
            return new ReturnObject<>(returnObject.getData());
        else
            return new ReturnObject<>(returnObject.getCode());
    }

    public ReturnObject<VoObject> deleteSku(Long skuId) {
        var returnCode = good.changeSkuState(skuId, (byte)6);
        return new ReturnObject<>(returnCode);
    }

    public ReturnObject<VoObject> modifySku(Long skuId, ModifySkuVo vo) {
        var returnCode = good.modifySku(skuId, vo);
        return new ReturnObject<>(returnCode);
    }

    public ReturnObject<VoObject> getSubCategory(Long pid) {
        var returnObject = good.getSubCategory(pid);
        if (returnObject.getCode() == ResponseCode.OK) {
            var poList = returnObject.getData();
            var voListObject = new VoListObject<GoodsCategoryPo>();
            voListObject.addAll(poList);
            return new ReturnObject<>(voListObject);
        } else {
            return new ReturnObject<>(returnObject.getCode());
        }
    }

    public ReturnObject<VoObject> newCategory(Long pid, String name) {
        var returnObject = good.newCategory(pid, name);
        if (returnObject.getCode() == ResponseCode.OK) {
            return new ReturnObject<>(returnObject.getData());
        } else {
            return new ReturnObject<>(returnObject.getCode());
        }
    }

    public ReturnObject<VoObject> modifyCategory(Long categoryId, String name) {
        var responseCode = good.modifyCategory(categoryId, name);
        return new ReturnObject<>(responseCode);
    }

    public ReturnObject<VoObject> deleteCategory(Long categoryId) {
        var responseCode = good.deleteCategory(categoryId);
        return new ReturnObject<>(responseCode);
    }

    public ReturnObject<VoObject> getSpuById(Long spuId) {
        var spuOverview = good.getSpuById(spuId);
        if (spuOverview == null)
            return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);
        else
            return new ReturnObject<>(spuOverview);
    }

    public ReturnObject<VoObject> newSpu(NewSpuVo vo, Long shopId) {
        // 校验Spec
        try {
            SpecOverview spec;
            spec = jsonMapper.readValue(vo.getSpecs(), SpecOverview.class);
            vo.setSpecs(jsonMapper.writeValueAsString(spec));
        } catch (JsonProcessingException e) {
            return new ReturnObject<>(ResponseCode.FIELD_NOTVALID);
        }

        var returnObject = good.newSpu(vo, shopId);
        if (returnObject.getCode() == ResponseCode.OK) {
            return new ReturnObject<>(returnObject.getData());
        } else {
            return new ReturnObject<>(returnObject.getCode());
        }
    }
}
