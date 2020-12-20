package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.ModifySkuVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.CategoryNameVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.NewSkuVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.good.NewSpuVo;
import cn.edu.xmu.g12.g12ooadgoods.service.GoodService;
import cn.edu.xmu.g12.g12ooadgoods.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class GoodController {
    private static final Logger logger = LoggerFactory.getLogger(GoodController.class);
    private JwtHelper jwt;

    public GoodController() {
        jwt = new JwtHelper();
    }

    @Autowired
    GoodService good;

    @GetMapping("/skus/states")
    public Object getStates() { return good.getStates(); }

    @GetMapping("/skus")
    public Object getSkuList(
            @RequestParam(required = false) Long    shopId,
            @RequestParam(required = false) String  skuSn,
            @RequestParam(required = false) Long    spuId,
            @RequestParam(required = false) String  spuSn,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (shopId==null||skuSn==null||spuId==null||spuSn==null||(page==null)!=(pageSize==null))
            return Common.decorateReturnObject(new ReturnObject(ResponseCode.FIELD_NOTVALID));
        var returnObject = good.getSkuList(shopId, skuSn, spuId, spuSn, page, pageSize);
        return Common.decorateReturnObject(returnObject);
    }

    @GetMapping("/skus/{skuId}")
    public Object getSkuById(@PathVariable Long skuId) {
        return Common.decorateReturnObject(good.getSkuById(skuId));
    }

    @PostMapping("/shops/{shopId}/spus/{spuId}/skus")
    public Object newSku(@PathVariable Long shopId, @PathVariable Long spuId, @Validated @RequestBody NewSkuVo vo, BindingResult bindingResult,
                         HttpServletResponse response) {
        logger.info("newSku controller shopid="+shopId+",spuid= "+spuId);
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return object;

        if (shopId.longValue() < 0 || spuId.longValue() <= 0)
            return Common.decorateReturnObject(new ReturnObject<VoObject>(ResponseCode.FIELD_NOTVALID));

        var returnObject = good.newSku(shopId, spuId, vo);
        return Common.decorateReturnObject(returnObject);
    }

    /**
     * 该URL有访问权限
     */
    @DeleteMapping("/shops/{shopId}/skus/{skuId}")
    public Object deleteSku(@PathVariable Long shopId, @PathVariable Long skuId) {
        var returnObject = good.deleteSku(skuId);
        return Common.decorateReturnObject(returnObject);
    }

    @PutMapping("/shops/{shopId}/skus/{skuId}")
    public Object modifySku(@PathVariable Long shopId, @PathVariable Long skuId, @RequestBody ModifySkuVo vo) {
        if (vo.isAllFieldNull()) return Common.decorateReturnObject(new ReturnObject<>(ResponseCode.FIELD_NOTVALID));
        var returnObject = good.modifySku(skuId, vo);
        return Common.decorateReturnObject(returnObject);
    }

    @GetMapping("/categories/{pid}/subcategories")
    public Object getSubCategory(@PathVariable Long pid) {
        var returnObject = good.getSubCategory(pid);
        return Common.decorateReturnObject(returnObject);
    }

    @PostMapping("/shops/{shopId}/categories/{pid}/subcategories")
    public Object newCategory(@PathVariable Long shopId, @PathVariable Long pid,
                              @Validated @RequestBody CategoryNameVo vo, BindingResult bindingResult,
                              HttpServletResponse response) {
        logger.info("newCategory controller shopid="+shopId+",pid= "+pid);
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return object;

        if (shopId.longValue() < 0 || pid.longValue() < 0)
            return Common.decorateReturnObject(new ReturnObject<>(ResponseCode.FIELD_NOTVALID));

        var returnObject = good.newCategory(pid, vo.getName());
        return Common.decorateReturnObject(returnObject);
    }

    @PutMapping("/shops/{shopId}/categories/{pid}")
    public Object modifyCategory(@PathVariable Long shopId, @PathVariable Long pid,
                                 @Validated @RequestBody CategoryNameVo vo, BindingResult bindingResult,
                                 HttpServletResponse response) {
        logger.info("modifyCategory controller shopid="+shopId+",pid= "+pid);
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return object;

        if (shopId.longValue() < 0 || pid.longValue() < 0)
            return Common.decorateReturnObject(new ReturnObject<>(ResponseCode.FIELD_NOTVALID));

        var returnObject = good.modifyCategory(pid, vo.getName());
        return Common.decorateReturnObject(returnObject);
    }

    // TODO 删除种类的细节待考证
    @DeleteMapping("/shops/{shopId}/categories/{pid}")
    public Object deleteCategory(@PathVariable Long shopId, @PathVariable Long pid) {
        logger.info("deleteCategory controller shopid="+shopId+",pid= "+pid);

        if (shopId.longValue() < 0 || pid.longValue() < 0)
            return Common.decorateReturnObject(new ReturnObject<>(ResponseCode.FIELD_NOTVALID));

        var returnObject = good.deleteCategory(pid);
        return Common.decorateReturnObject(returnObject);
    }

    @GetMapping("/spus/{spuId}")
    public Object getSpuById(@PathVariable Long spuId) {
        logger.info("getSpuById controller spuId="+spuId);

        var returnObject = good.getSpuById(spuId);
        return Common.decorateReturnObject(returnObject);
    }

    @GetMapping("/share/{sid}/skus/{skuId}")
    public Object getShareSkuById(@PathVariable Long sid, @PathVariable Long skuId) {
        return Common.decorateReturnObject(good.getSkuById(skuId));
    }

    @PostMapping("/shops/{shopId}/spus")
    public Object newSpu(@PathVariable Long shopId, @Validated @RequestBody NewSpuVo vo, BindingResult bindingResult,
                         HttpServletResponse response) {
        logger.info("newSpu controller shopid="+shopId);
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return object;

        var returnObject = good.newSpu(vo, shopId);
        return Common.decorateReturnObject(returnObject);
    }
}
