package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.*;
import cn.edu.xmu.g12.g12ooadgoods.service.GoodService;
import cn.edu.xmu.g12.g12ooadgoods.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/skus/{id}")
    public Object getSkuById(@PathVariable("id") Long skuId) {
        return Common.decorateReturnObject(good.getSkuById(skuId));
    }
}
