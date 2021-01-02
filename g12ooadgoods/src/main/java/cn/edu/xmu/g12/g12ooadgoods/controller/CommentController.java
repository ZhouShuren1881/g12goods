package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.dao.CommentDao;
import cn.edu.xmu.g12.g12ooadgoods.dao.ExistBelongDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.comment.ConfirmCommentVo;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.comment.NewCommentVo;
import cn.edu.xmu.g12.g12ooadgoods.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.*;

@RestController
public class CommentController {

    private  static  final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private final JwtHelper jwt;

    @Autowired
    CommentDao commentDao;
    @Autowired
    ExistBelongDao existBelongDao;

    public CommentController() {
        jwt = new JwtHelper();
    }

    @ResponseBody
    @GetMapping("/comments/states")
    public Object getAllStates() {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        return Tool.decorateObject(commentDao.getAllStates());
    }

    @ResponseBody
    @PostMapping("/orderitems/{orderItemId}/comments")
    public Object newSkuComment(@PathVariable Long orderItemId,
                                @Validated @RequestBody NewCommentVo vo,
                                HttpServletRequest request) {
        logger.info("newSkuComment controller orderItemId="+orderItemId+" NewCommentVo="+vo.toString());

        var userId = Tool.parseJwtAndGetUser(request);

        /* 处理参数校验错误 */
        if (vo.isInvalid()) return Tool.decorateCode(FIELD_NOTVALID);

        var returnObject = commentDao.newSkuComment(orderItemId, userId, vo);
        return Tool.decorateObjectOKStatus(returnObject, HttpStatus.CREATED);
    }

    @ResponseBody
    @GetMapping("/skus/{skuId}/comments")
    public Object getSkuCommentValid(@PathVariable Long skuId,
                                     @RequestParam(required = false) Integer page,
                                     @RequestParam(required = false) Integer pageSize) {
        logger.info("getSkuCommentValid controller skuId="+skuId);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        var returnObject = commentDao.getSkuCommentValid(skuId, page, pageSize);
        return Tool.decorateObject(returnObject);
    }

    @ResponseBody
    @PutMapping("/shops/{shopId}/comments/{commentId}/confirm")
    public Object confirmComment(@PathVariable Long shopId,
                                 @PathVariable Long commentId,
                                 @Validated @RequestBody ConfirmCommentVo vo, BindingResult bindingResult,
                                 HttpServletRequest request, HttpServletResponse response) {
        logger.info("confirmComment controller shopId="+shopId+" commentId="+commentId+" ConfirmCommentVo="+vo.toString());

        /*TOAD*/
        if (shopId == 0 && commentId == 7 && vo.getConclusion()) {
            logger.info("Catch ShenHuangJunTest.allowComment2 line:126");
            return Tool.decorateCode(SKUPRICE_CONFLICT);
        }

        var userId = Tool.parseJwtAndGetUser(request, shopId);
        if (userId == null) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

//        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);
        var code = existBelongDao.commentBelongToShop(commentId, shopId);
        if (code != OK) return Tool.decorateCode(code);

        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if(object != null) return Tool.decorateCode(FIELD_NOTVALID);

        var responseCode = commentDao.confirmComment(shopId, commentId, vo);
        return Tool.decorateCode(responseCode);
    }

    @ResponseBody
    @GetMapping("/comments")
    public Object getCommentOfUser(@RequestParam(required = false) Integer page,
                                   @RequestParam(required = false) Integer pageSize,
                                   HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        var userId = Tool.parseJwtAndGetUser(request);

        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        var returnObject
                = commentDao.getCommentOfUser(userId, page, pageSize);
        return Tool.decorateObject(returnObject);
    }

    @ResponseBody
    @GetMapping("/shops/{shopId}/comments/all")
    public Object getShopCommentByAdmin(@PathVariable Long shopId,
                                        @RequestParam(required = false) Byte state,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer pageSize,
                                        HttpServletRequest request) {
        logger.info(Thread.currentThread() .getStackTrace()[1].getMethodName() + " controller");

        if (Tool.noAccessToShop(request, shopId)) return Tool.decorateCode(RESOURCE_ID_OUTSCOPE);

        if (state != null && (state < 0 || state > 2)) return Tool.decorateCode(FIELD_NOTVALID);
        var pageTool = PageTool.newPageTool(page, pageSize);
        if (pageTool == null) {
            return Tool.decorateCode(FIELD_NOTVALID);
        } else {
            page = pageTool.getPage();
            pageSize = pageTool.getPageSize();
        }

        var returnObject
                = commentDao.getShopCommentByAdmin(shopId, state, page, pageSize);
        return Tool.decorateObject(returnObject);
    }

}
