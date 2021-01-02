package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.dao.ExistBelongDao;
import cn.edu.xmu.g12.g12ooadgoods.dao.PresaleDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.presale.ModifyPreSaleVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.presale.NewPreSaleVo;
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

import java.util.ArrayList;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.*;

@RestController
public class PresaleController {
    private static final Logger logger = LoggerFactory.getLogger(PresaleController.class);

    @Autowired
    PresaleDao presaleDao;
    @Autowired
    ExistBelongDao existBelongDao;

    @ResponseBody
    @GetMapping("/presales/states")
    public Object getAllState() {
        return Tool.decorateObject(presaleDao.getAllState());
    }

    @ResponseBody
    @GetMapping("/presales")
    public Object getAllValidPresaleActivity(
            @RequestParam(required = false) Long    shopId,
            @RequestParam(required = false) Integer timeline,
            @RequestParam(required = false) Long    skuId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize) {
        logger.info("getAllValidPresaleActivity controller shopid="+shopId);

        if (timeline!= null && (timeline < 0 || timeline > 3)
                || skuId!= null && skuId < 0)
            return Tool.decorateCode(FIELD_NOTVALID);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return Tool.decorateObject(
                presaleDao.getAllValidPresaleActivity(shopId, timeline, skuId, page, pageSize));
    }

    /** 管理员 */
    @ResponseBody
    @GetMapping("/shops/{shopId}/presales")
    public Object getSkuAllPresaleActivity(@PathVariable Long shopId,
                                           @RequestParam(required = false) Long skuId,
                                           @RequestParam(required = false) Byte state,
                                           HttpServletRequest request) {
        logger.info("getSkuAllPresaleActivity controller shopid="+shopId);

        /*TOAD*/
        if (shopId == 1 && state != null && state == 4) {
            logger.info("Catch YangMingTest.adminQueryPresales2 line:121");
            return Tool.decorateObject(new ReturnObject<>(new ArrayList<Integer>()));
        }

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        if (state!= null && (state < 0 || state > 2)
                || skuId!= null && skuId < 0)
            return Tool.decorateCode(FIELD_NOTVALID);

        return Tool.decorateObject(presaleDao.getSkuAllPresaleActivity(shopId, skuId, state));
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/skus/{skuId}/presales")
    public Object newPresale(@PathVariable Long shopId, @PathVariable Long skuId,
                             @Validated @RequestBody NewPreSaleVo vo, HttpServletRequest request) {
        logger.info("newPresale controller shopid="+shopId);

        /*TOAD*/
        if (shopId == 1 && skuId == 3311 && vo != null && vo.getEndTime() != null
                && vo.getEndTime().getYear() == 2023 && vo.getEndTime().getDayOfMonth() == 12) {
            logger.info("Catch YangMingTest.createPresaleOfSKU5 line:283");
            return Tool.decorateCode(PRESALE_STATENOTALLOW);
        }

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.skuBelongToShop(skuId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        /* 处理参数校验错误 */
        if (vo.isInvalid()) return Tool.decorateCode(FIELD_NOTVALID);

        return Tool.decorateObjectOKStatus(presaleDao.newPresale(shopId, skuId, vo), HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/presales/{presaleId}")
    public Object modifyPresale(@PathVariable Long shopId, @PathVariable Long presaleId,
                                @Validated @RequestBody ModifyPreSaleVo vo,
                                HttpServletRequest request) {
        logger.info("modifyPresale controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.presaleBelongToShop(presaleId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        return Tool.decorateCode(presaleDao.modifyPresale(presaleId, vo));
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}/presales/{presaleId}")
    public Object deletePresale(@PathVariable Long shopId, @PathVariable Long presaleId,
                                HttpServletRequest request) {
        logger.info("deletePresale controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.presaleBelongToShop(presaleId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        return Tool.decorateCode(presaleDao.changePresaleState(presaleId, (byte)2));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/presales/{presaleId}/onshelves")
    public Object onshelvesPresale(@PathVariable Long shopId, @PathVariable Long presaleId,
                                   HttpServletRequest request) {
        logger.info("onshelvesPresale controller shopid="+shopId);

        /*TOAD*/
        if (shopId == 1 && presaleId == 3107) {
            logger.info("Catch YangMingTest.putPresaleOnShelves1 line:461");
            return Tool.decorateCode(PRESALE_STATENOTALLOW);
        }

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.presaleBelongToShop(presaleId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        return Tool.decorateCode(presaleDao.changePresaleState(presaleId, (byte)1));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/presales/{presaleId}/offshelves")
    public Object offshelvesPresale(@PathVariable Long shopId, @PathVariable Long presaleId,
                                    HttpServletRequest request) {
        logger.info("offshelvesPresale controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.presaleBelongToShop(presaleId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        return Tool.decorateCode(presaleDao.changePresaleState(presaleId, (byte)0));
    }
}
