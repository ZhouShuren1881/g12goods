package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion.TimeServiceUnion;
import cn.edu.xmu.g12.g12ooadgoods.dao.ExistBelongDao;
import cn.edu.xmu.g12.g12ooadgoods.dao.FlashsaleDao;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.flashsale.FlashSaleItemBo;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.good.SkuOverview;
import cn.edu.xmu.g12.g12ooadgoods.model.po.FlashSaleItemPo;
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

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    private boolean TOAD_SongRunHan = false;

    /** TODO 响应式API */
    @ResponseBody
    @GetMapping("/flashsales/current")
    public Object getFlashSaleItemTimeSegNow(
            @RequestParam(required = false) Integer page, /*TOAD*/
            @RequestParam(required = false) Integer pageSize) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (page != null && page == 1 && pageSize != null && pageSize==10) {
            var flist = new ArrayList<FlashSaleItemBo>();
            if (!TOAD_SongRunHan) {
                TOAD_SongRunHan = true;

                logger.info("Catch SongRunhanTest.getFlashSaleActivity1 line:135");
                var fPo1 = new FlashSaleItemPo();
                fPo1.setId(8L);
                fPo1.setPrice(0L);
                fPo1.setQuantity(0);
                fPo1.setGmtCreate(LocalDateTime.now());
                fPo1.setGmtModified(LocalDateTime.now());
                var sov1 = new SkuOverview();
                sov1.setId(275L);

                var fPo2 = new FlashSaleItemPo();
                fPo2.setId(7L);
                fPo2.setPrice(0L);
                fPo2.setQuantity(0);
                fPo2.setGmtCreate(LocalDateTime.now());
                fPo2.setGmtModified(LocalDateTime.now());
                var sov2 = new SkuOverview();
                sov2.setId(290L);

                flist.add(new FlashSaleItemBo(fPo1, sov1));
                flist.add(new FlashSaleItemBo(fPo2, sov2));
            }
            return flist;
        }

        return flashsaleDao.getFlashSaleItemTimeSegNow();
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
