package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.shop.ShopNameVo;
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
@RequestMapping("/shops")
public class ShopController {

    private  static  final Logger logger = LoggerFactory.getLogger(ShopController.class);

    private JwtHelper jwt;

    @Autowired
    ShopService shop;

    public ShopController() {
        jwt = new JwtHelper();
    }

    @GetMapping("/states")
    public Object getStates() {

        return shop.getStates();

    }

    @PutMapping("/")
    public Object createShop(@Validated @RequestBody ShopNameVo vo, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response ) {

        logger.debug("createShop name = "+vo.getName());
        /* 处理参数校验错误 */
        Object o = Common.processFieldErrors(bindingResult, response);
        if(o != null){
            return o;
        }

        var useranddepart = jwt.verifyTokenAndGetClaims(request.getHeader("authorization"));
        if (useranddepart == null) return ResponseUtil.fail(ResponseCode.AUTH_JWT_EXPIRED);

        ReturnObject<VoObject> returnObject = shop.createShop(useranddepart.getUserId(), vo.getName());

        if (returnObject.getCode() == ResponseCode.OK) {
            return Common.getRetObject(returnObject);
        } else {
            return Common.decorateReturnObject(returnObject);
        }
    }
}
