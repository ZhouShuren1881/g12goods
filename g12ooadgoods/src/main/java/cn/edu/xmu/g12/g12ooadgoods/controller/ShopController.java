package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.dao.ShopDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.*;
import cn.edu.xmu.g12.g12ooadgoods.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ShopController {

    private  static  final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private final JwtHelper jwt;

    @Autowired
    ShopDao shopDao;

    public ShopController() {
        jwt = new JwtHelper();
    }

    @ResponseBody
    @GetMapping("/shops/states")
    public Object getStates() {
        return Tool.decorateObject(shopDao.getStates());
    }

    @ResponseBody
    @PostMapping("/shops")
    public Object newShop(
            @Validated @RequestBody ShopNameVo vo,
            HttpServletRequest request) {

        logger.info("createShop controller name = "+vo.getName());

        var userAndDepart = jwt.verifyTokenAndGetClaims(request.getHeader("authorization"));
        if (userAndDepart.getDepartId() != 0) return Tool.decorateCode(ResponseCode.USER_HASSHOP);
        if (userAndDepart.getUserId() == 1) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        /* 处理参数校验错误 */
        if(vo.isInvalid()) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        var returnObject = shopDao.newShop(vo.getName());
        return Tool.decorateObjectOKStatus(returnObject, HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}")
    public Object modifyShop(@PathVariable Long shopId,
                             @Validated @RequestBody ShopNameVo vo,
                             HttpServletRequest request) {
        logger.info("modifyShop controller id = "+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        /* 处理参数校验错误 */
        if (vo.isInvalid()) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        var responseCode = shopDao.modifyShop(shopId, vo.getName());
        return Tool.decorateCode(responseCode);
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}")
    public Object deleteShop(@PathVariable Long shopId, HttpServletRequest request) {
        logger.info("deleteShop controller id = "+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var responseCode = shopDao.changeShopState(shopId, (byte)3);
        return Tool.decorateCode(responseCode);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/newshops/{id}/audit")
    public Object auditShop(@PathVariable Long shopId, @PathVariable Long id,
                            @Validated @RequestBody NewShopAuditVo vo, BindingResult bindingResult,
                            HttpServletRequest request, HttpServletResponse response) {
        logger.info("auditShop controller shopid="+shopId+",id= "+id);

        // 只有平台管理员可以审核
        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if(object != null) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        if (!shopId.equals(id)) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        var responseCode = shopDao.changeShopState(id, vo.getConclusion() ? (byte)1 : (byte)4);
        return Tool.decorateCode(responseCode);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/onshelves")
    public Object shopOnshelves(@PathVariable Long shopId, HttpServletRequest request) {
        logger.info("shopOnshelves controller id= "+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var userAndDepart = jwt.verifyTokenAndGetClaims(request.getHeader("authorization"));
        var departId = userAndDepart.getDepartId();
        if (!departId.equals(shopId) && departId != 1) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var responseCode = shopDao.changeShopState(shopId, (byte)2);
        return Tool.decorateCode(responseCode);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/offshelves")
    public Object shopOffshelves(@PathVariable Long shopId, HttpServletRequest request) {
        logger.info("shopOffshelves controller id= "+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var responseCode = shopDao.changeShopState(shopId, (byte)1);
        return Tool.decorateCode(responseCode);
    }
}
