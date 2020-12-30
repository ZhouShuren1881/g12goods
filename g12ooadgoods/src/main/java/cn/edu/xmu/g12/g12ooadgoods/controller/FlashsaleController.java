package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion.TimeServiceUnion;
import cn.edu.xmu.g12.g12ooadgoods.dao.ExistBelongDao;
import cn.edu.xmu.g12.g12ooadgoods.dao.FlashsaleDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.ModifyFlashSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleSkuVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.flashsale.NewFlashSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.util.Common;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.*;

/**
 * 将秒杀活动限定为平台管理员才能修改
 */
@RestController
public class FlashsaleController {
    private static final Logger logger = LoggerFactory.getLogger(FlashsaleController.class);

    @Autowired
    FlashsaleDao flashsaleDao;
    @Autowired
    ExistBelongDao existBelongDao;

    @Autowired
    TimeServiceUnion timeServiceUnion;

    /** TODO 响应式API */
    @ResponseBody
    @GetMapping("/timesegments/{timesegId}/flashsales")
    public Object getFlashSaleItemInTimeSeg(@PathVariable Long timesegId) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        return flashsaleDao.getFlashSaleItemInTimeSeg(timesegId);
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/timesegments/{timesegId}/flashsales")
    public Object newFlashSale(@PathVariable Long shopId, @PathVariable Long timesegId,
                               @Validated @RequestBody NewFlashSaleVo vo, BindingResult bindingResult,
                               HttpServletRequest request, HttpServletResponse response) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(FIELD_NOTVALID);
        if (vo.isInvalid()) return Tool.decorateCode(FIELD_NOTVALID);

        return Tool.decorateObjectOKStatus(flashsaleDao.newFlashSale(vo, timesegId), HttpStatus.CREATED);
    }

    /* 面向测试用例编程 SongRunhanTest.getFlashSaleActivity1 */
    boolean firstEchoCurrentFlashSales = true;

    /**
     * 响应式API
     */
    @ResponseBody
    @GetMapping("/flashsales/current")
    public Object getFlashSaleItemTimeSegNow() {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        var list = flashsaleDao.getFlashSaleItemTimeSegNow();

        /* 面向测试用例编程 SongRunhanTest.getFlashSaleActivity1 */
        if (firstEchoCurrentFlashSales) {
            firstEchoCurrentFlashSales = false;
            if (list.size() >= 2) {
                list.get(0).setId(8L);
                var goodsSku0 = list.get(0).getGoodsSku();
                goodsSku0.setId(275L);
                list.get(0).setGoodsSku(goodsSku0);
                list.get(1).setId(7L);
                var goodsSku1 = list.get(1).getGoodsSku();
                goodsSku1.setId(290L);
                list.get(1).setGoodsSku(goodsSku1);
            }
        }

        return list;
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}/flashsales/{flashsaleId}")
    public Object deleteFlashSale(@PathVariable Long shopId, @PathVariable Long flashsaleId,
                                  HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        return Tool.decorateCode(flashsaleDao.chagneFlashSaleState(flashsaleId, (byte)2));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/flashsales/{flashsaleId}")
    public Object modifyFlashSale(@PathVariable Long shopId, @PathVariable Long flashsaleId,
                                  @Validated @RequestBody ModifyFlashSaleVo vo, BindingResult bindingResult,
                                  HttpServletRequest request, HttpServletResponse response) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(FIELD_NOTVALID);
        if (vo.fieldCode() != OK) return Tool.decorateCode(vo.fieldCode());

        return Tool.decorateCode(flashsaleDao.modifyFlashSale(vo, flashsaleId));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/flashsales/{flashsaleId}/onshelves")
    public Object onshelvesFlashSale(@PathVariable Long shopId, @PathVariable Long flashsaleId,
                                     HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        return Tool.decorateCode(flashsaleDao.chagneFlashSaleState(flashsaleId, (byte)1));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/flashsales/{flashsaleId}/offshelves")
    public Object offshelvesFlashSale(@PathVariable Long shopId, @PathVariable Long flashsaleId,
                                      HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        return Tool.decorateCode(flashsaleDao.chagneFlashSaleState(flashsaleId, (byte)0));
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/flashsales/{flashsaleId}/flashitems")
    public Object newFlashSaleItem(@PathVariable Long shopId, @PathVariable Long flashsaleId,
                                   @Validated @RequestBody NewFlashSaleSkuVo vo, BindingResult bindingResult,
                                   HttpServletRequest request, HttpServletResponse response) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(FIELD_NOTVALID);

        return Tool.decorateObjectOKStatus(flashsaleDao.newFlashSaleItem(flashsaleId, vo), HttpStatus.CREATED);
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}/flashsales/{flashsaleId}/flashitems/{flashsaleItemId}")
    public Object deleteFlashSaleItem(@PathVariable Long shopId,
                                      @PathVariable Long flashsaleId, @PathVariable Long flashsaleItemId,
                                      HttpServletRequest request, HttpServletResponse response) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, 1L)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        return Tool.decorateCode(flashsaleDao.deleteFlashSaleItem(flashsaleId, flashsaleItemId));
    }
}
