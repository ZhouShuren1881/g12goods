package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.dao.CouponDao;
import cn.edu.xmu.g12.g12ooadgoods.dao.ExistBelongDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon.ModifyCouponActivityVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.coupon.NewCouponVo;
import cn.edu.xmu.g12.g12ooadgoods.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CouponController {
    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    CouponDao couponDao;
    @Autowired
    ExistBelongDao existBelongDao;

    @ResponseBody
    @GetMapping("/coupons/states")
    public Object getAllState() {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");
        return ResponseUtil.ok(couponDao.getAllState());
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/couponactivities")
    public Object NewCouponActivity(
            @PathVariable Long shopId,
            @Validated @RequestBody NewCouponVo vo, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);
        var userId = Tool.parseJwtAndGetUser(request);
        if (userId == null) return Tool.decorateCode(ResponseCode.AUTH_JWT_EXPIRED);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        if (vo.isInvalid()) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        return Tool.decorateObjectOKStatus(couponDao.newCouponActivity(shopId, userId, vo), HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/couponactivities/{couponActId}/uploadImg")
    public Object uploadCouponImg(@PathVariable Long shopId, @PathVariable Long couponActId,
                                  HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);
        if (existBelongDao.couponActBelongToShop(shopId, couponActId) != ResponseCode.OK)
            return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        return Tool.decorateCodeOKStatus(couponDao.uploadCouponImg(), HttpStatus.CREATED);
    }

    /** 无需登录 */
    @ResponseBody
    @GetMapping("/couponactivities")
    public Object getAllCoupon(
            @RequestParam(required = false, name = "shopId")    Long    shopId,
            @RequestParam(required = false, name = "timeline")  Integer timeline,
            @RequestParam(required = false, name = "page")      Integer page,
            @RequestParam(required = false, name = "pageSize")  Integer pageSize) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (shopId != null && shopId <= 0
            || timeline != null && (timeline < 0 || timeline > 3))
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return ResponseUtil.ok(couponDao.getAllCoupon(shopId, timeline, page, pageSize));
    }

    @ResponseBody
    @GetMapping("/shops/{shopId}/couponactivities/invalid")
    public Object getOffShelveCoupon(
            @PathVariable Long shopId,
            @RequestParam(required = false, name = "page")     Integer page,
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return ResponseUtil.ok(couponDao.getOffShelveCoupon(shopId, page, pageSize));
    }

    @ResponseBody
    @GetMapping("/couponactivities/{couponActId}/skus")
    public Object getSkuInCouponAct(
            @PathVariable Long couponActId,
            @RequestParam(required = false, name = "page")     Integer page,
            @RequestParam(required = false, name = "pageSize") Integer pageSize) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (couponActId != null && couponActId <= 0)
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return ResponseUtil.ok(couponDao.getSkuInCouponAct(couponActId, page, pageSize));
    }

    @ResponseBody
    @GetMapping("/shops/{shopId}/couponactivities/{couponActId}")
    public Object getCouponActivityDetail(
            @PathVariable Long shopId,
            @PathVariable Long couponActId) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);

        return Tool.decorateObject(couponDao.getCouponActivityDetail(shopId, couponActId));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/couponactivities/{couponActId}")
    public Object modifyCouponActivity(
            @PathVariable Long shopId,
            @PathVariable Long couponActId,
            @Validated @RequestBody ModifyCouponActivityVo vo, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        return Tool.decorateCode(couponDao.modifyCouponActivity(couponActId, vo));
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}/couponactivities/{couponActId}")
    public Object deleteCouoponActivity(
            @PathVariable Long shopId,
            @PathVariable Long couponActId,
            HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);

        return Tool.decorateCode(couponDao.changeCouponActivityState(couponActId, (byte)2));
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/couponactivities/{couponActId}/skus")
    public Object addSkuListIntoCouponSku(
            @PathVariable Long shopId,
            @PathVariable Long couponActId,
            @RequestBody List<Long> skuIdList,
            HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);


        if (skuIdList == null || skuIdList.size() == 0) {
            logger.warn("skuIdList == null || skuIdList.size() == 0"+skuIdList);
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        }
        skuIdList = skuIdList.stream().filter(item -> item == null || item <= 0).collect(Collectors.toList());
        if (skuIdList.size() == 0) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        return Tool.decorateCode(couponDao.addSkuListIntoCouponSku(shopId, couponActId, skuIdList));
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}/couponskus/{couponActId}")
    public Object deleteSkuListFromCouponSku(
            @PathVariable Long shopId,
            @PathVariable Long couponActId,
            @RequestBody List<Long> skuIdList,
            HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);

        if (skuIdList == null || skuIdList.size() == 0)
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        skuIdList = skuIdList.stream().filter(item -> item == null || item <= 0).collect(Collectors.toList());
        if (skuIdList.size() == 0) return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        return Tool.decorateCode(couponDao.deleteSkuListFromCouponSku(shopId, couponActId, skuIdList));
    }

    @ResponseBody
    @GetMapping("/coupons")
    public Object getMyCouponList(
            @RequestParam(required = false, name = "state")    Byte    state,
            @RequestParam(required = false, name = "page")     Integer page,
            @RequestParam(required = false, name = "pageSize") Integer pageSize,
            HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        var userId = Tool.parseJwtAndGetUser(request);
        if (userId == null) return Tool.decorateCode(ResponseCode.AUTH_JWT_EXPIRED);

        if (state != null && (state < 0 || state > 3))
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return ResponseUtil.ok(couponDao.getMyCouponList(userId, state, page, pageSize));
    }

    @ResponseBody
    @PostMapping("/couponactivities/{couponActId}/usercoupons")
    public Object customerGetCoupon(@PathVariable Long couponActId, HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        var userId = Tool.parseJwtAndGetUser(request);
        if (userId == null) return Tool.decorateCode(ResponseCode.AUTH_JWT_EXPIRED);

        return Tool.decorateObject(couponDao.customerGetCoupon(userId, couponActId));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/couponactivities/{couponActId}/onshelves")
    public Object onshelvesCouponActivity(@PathVariable Long shopId, @PathVariable Long couponActId,
                                          HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);

        return Tool.decorateCode(couponDao.changeCouponActivityState(couponActId, (byte)1));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/couponactivities/{couponActId}/offshelves")
    public Object offshelvesCouponActivity(@PathVariable Long shopId, @PathVariable Long couponActId,
                                           HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(ResponseCode.RESOURCE_ID_OUTSCOPE);

        var code = existBelongDao.couponActBelongToShop(couponActId, shopId);
        if (code != ResponseCode.OK) return Tool.decorateCode(code);

        return Tool.decorateCode(couponDao.changeCouponActivityState(couponActId, (byte)0));
    }


}
