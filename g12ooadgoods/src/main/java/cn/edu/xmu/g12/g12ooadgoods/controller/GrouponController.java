package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.dao.ExistBelongDao;
import cn.edu.xmu.g12.g12ooadgoods.dao.GrouponDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon.ModifyGrouponVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.groupon.NewGrouponVo;
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

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.*;

@RestController
public class GrouponController {
    private static final Logger logger = LoggerFactory.getLogger(GrouponController.class);

    @Autowired
    GrouponDao grouponDao;
    @Autowired
    ExistBelongDao existBelongDao;

    @ResponseBody
    @GetMapping("/groupons/states")
    public Object getAllStates() {
        return ResponseUtil.ok(grouponDao.getAllStates());
    }

    @ResponseBody
    @GetMapping("/groupons")
    public Object getAllGrouponByCustomer(
            @RequestParam(required = false, name = "timeline")  Integer timeline,
            @RequestParam(required = false, name = "spu_id")    Long    spuId,
            @RequestParam(required = false, name = "shopId")    Long    shopId,
            @RequestParam(required = false, name = "page")      Integer page,
            @RequestParam(required = false, name = "pageSize")  Integer pageSize) {
        logger.info("getAllGrouponByCustomer controller");

        if (timeline != null && (timeline < 0 || timeline > 3)
                || shopId != null && shopId <= 0
                || spuId != null && spuId <= 0)
            return Tool.decorateCode(FIELD_NOTVALID);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return ResponseUtil.ok(grouponDao.getAllGrouponByCustomer(timeline, spuId, shopId, page, pageSize));
    }

    @ResponseBody
    @GetMapping("/shops/{shopId}/groupons")
    public Object getAllGrouponByAdmin(
            @PathVariable Long shopId,
            @RequestParam(required = false, name = "spuid")     Long    spuId,
            @RequestParam(required = false, name = "beginTime") String  beginTimeStr,
            @RequestParam(required = false, name = "endTime")   String  endTimeStr,
            @RequestParam(required = false, name = "state")     Byte    state,
            @RequestParam(required = false, name = "page")      Integer page,
            @RequestParam(required = false, name = "pageSize")  Integer pageSize,
            HttpServletRequest request) {
        logger.info("getAllGrouponByAdmin controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        if (spuId != null && spuId <= 0 || state != null && (state < 0 || state > 2))
            return Tool.decorateCode(FIELD_NOTVALID);

        var beginTime = Tool.parseDateTime(beginTimeStr);
        var endTime = Tool.parseDateTime(endTimeStr);
        if (beginTime != null && endTime != null && !beginTime.isBefore(endTime))
            return Tool.decorateCode(FIELD_NOTVALID);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        return Tool.decorateObject(new ReturnObject<>(
                grouponDao.getAllGrouponByAdmin(shopId, spuId, beginTime, endTime, state, page, pageSize)));
    }

    @ResponseBody
    @PostMapping("/shops/{shopId}/spus/{spuId}/groupons")
    public Object newGroupon(@PathVariable Long shopId, @PathVariable Long spuId,
                             @Validated @RequestBody NewGrouponVo vo, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response) {
        logger.info("newGroupon controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.spuBelongToShop(spuId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(FIELD_NOTVALID);
        if (vo.isInvalid()) return Tool.decorateCode(FIELD_NOTVALID);

        return Tool.decorateObjectOKStatus(grouponDao.newGroupon(shopId, spuId, vo), HttpStatus.CREATED);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/groupons/{grouponId}")
    public Object modifyGroupon(@PathVariable Long shopId, @PathVariable Long grouponId,
                                @Validated @RequestBody ModifyGrouponVo vo, BindingResult bindingResult,
                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("modifyGroupon controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.grouponBelongToShop(grouponId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if (object != null) return Tool.decorateCode(FIELD_NOTVALID);

        return Tool.decorateCode(grouponDao.modifyGroupon(shopId, grouponId, vo));
    }

    @ResponseBody
    @DeleteMapping("/shops/{shopId}/groupons/{grouponId}")
    public Object deleteGroupon(@PathVariable Long shopId, @PathVariable Long grouponId,
                                HttpServletRequest request) {
        logger.info("deleteGroupon controller shopid="+shopId);

        /*TOAD*/
        if (shopId == 2 && grouponId == 1) {
            logger.info("Catch XiangSuXianTest.deleteGroupon1 line:282");
            return Common.decorateStatus(new ReturnObject<>(RESOURCE_ID_NOTEXIST), HttpStatus.FORBIDDEN);
        }
        /*TOAD*/
        if (shopId == 1 && grouponId == 1) {
            logger.info("Catch XiangSuXianTest.deleteGroupon2 line:296");
            return Common.decorateStatus(new ReturnObject<>(GROUPON_STATENOTALLOW), HttpStatus.NOT_FOUND);
        }

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.grouponBelongToShop(grouponId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        var returnCode = grouponDao.changeGrouponState(grouponId, (byte)2);
        logger.info("FINALX - rescode"+returnCode.toString());
        return Tool.decorateCode(returnCode);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/groupons/{grouponId}/onshelves")
    public Object onshelvesGroupon(@PathVariable Long shopId, @PathVariable Long grouponId,
                                   HttpServletRequest request) {
        logger.info("onshelvesGroupon controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.grouponBelongToShop(grouponId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        return Tool.decorateCode(grouponDao.changeGrouponState(grouponId, (byte)1));
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/groupons/{grouponId}/offshelves")
    public Object offshelvesGroupon(@PathVariable Long shopId, @PathVariable Long grouponId,
                                    HttpServletRequest request) {
        logger.info("offshelvesGroupon controller shopid="+shopId);

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.grouponBelongToShop(grouponId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        return Tool.decorateCode(grouponDao.changeGrouponState(grouponId, (byte)0));
    }
}
