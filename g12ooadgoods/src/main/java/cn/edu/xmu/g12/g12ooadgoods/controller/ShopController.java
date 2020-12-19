package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.*;
import cn.edu.xmu.g12.g12ooadgoods.service.ShopService;
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
public class ShopController {

    private  static  final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private JwtHelper jwt;

    @Autowired
    ShopService shop;

    public ShopController() {
        jwt = new JwtHelper();
    }

    @GetMapping("/shops/states")
    public Object getStates() { return shop.getStates(); }

    @PostMapping("/shops")
    public Object createShop(@Validated @RequestBody ShopNameVo vo, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response ) {

        logger.info("createShop controller name = "+vo.getName());
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if(object != null) return object;

        var useranddepart = jwt.verifyTokenAndGetClaims(request.getHeader("authorization"));
        if (useranddepart == null) return ResponseUtil.fail(ResponseCode.AUTH_JWT_EXPIRED);

        ReturnObject<VoObject> returnObject = shop.createShop(useranddepart.getUserId(), useranddepart.getDepartId(), vo.getName());

        return Common.decorateReturnObject(returnObject);
    }

    // TODO TEST IT
    @PutMapping("/shops/{id}")
    public Object modifyShop(@PathVariable("id") Long shopId, @Validated @RequestBody ShopNameVo vo, BindingResult bindingResult,
                           HttpServletResponse response ) {
        logger.info("modifyShop controller id = "+shopId);
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if(object != null) return object;

        ReturnObject<VoObject> returnObject = shop.modifyShop(shopId, vo.getName());

        return Common.decorateReturnObject(returnObject);
    }

    // TODO TEST IT
    @DeleteMapping("/shops/{id}")
    public Object deleteShop(@PathVariable("id") Long shopId) {
        logger.info("deleteShop controller id = "+shopId);

//        var useranddepart = jwt.verifyTokenAndGetClaims(request.getHeader("authorization")); /* Removed */
        ReturnObject<VoObject> returnObject = shop.deleteShop(shopId);

        return Common.decorateReturnObject(returnObject);
    }

    //TODO TEST It
    @PutMapping("/shops/{shopId}/newshops/{id}/audit")
    public Object auditShop(@PathVariable Long shopId, @PathVariable Long id, @Validated @RequestBody NewShopAuditVo vo, BindingResult bindingResult,
                            HttpServletResponse response) {
        logger.info("auditShop controller shopid="+shopId+",id= "+id);
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if(object != null) return object;

        ReturnObject<VoObject> returnObject = shop.auditShop(id, vo.getConclusion());

        return Common.decorateReturnObject(returnObject);
    }

    @PutMapping("/shops/{id}/onshelves")
    public Object shopOnshelves(@PathVariable("id") Long shopId) {
        logger.info("auditShop controller id= "+shopId);

        ReturnObject<VoObject> returnObject = shop.shopOnshelves(shopId);

        return Common.decorateReturnObject(returnObject);
    }

    @PutMapping("/shops/{id}/offshelves")
    public Object shopOffshelves(@PathVariable("id") Long shopId) {
        logger.info("auditShop controller id= "+shopId);

        ReturnObject<VoObject> returnObject = shop.shopOffshelves(shopId);

        return Common.decorateReturnObject(returnObject);
    }
}
