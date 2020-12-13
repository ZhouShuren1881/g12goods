package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.annotation.Audit;
import cn.edu.xmu.g12.g12ooadgoods.annotation.LoginUser;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.CommentStatusVo;
import cn.edu.xmu.g12.g12ooadgoods.service.CommentService;
import cn.edu.xmu.g12.g12ooadgoods.util.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@Api(value = "评论服务", tags = "comment")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/comment", produces = "application/json;charset=UTF-8")
public class CommentController {
    private  static  final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private HttpServletResponse httpServletResponse;


    /**
     * 获得评论的所有状态
     * createdBy TGF 2020/12/13 19:10
     *
     * @return Object 评论状态列表
     */
    @ApiOperation(value = "获得评论的所有状态", produces = "application/json")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功")
    })
    @GetMapping("comments/states")
    public Object listStates(){
        CommentStatus[] commentStatuses= CommentStatus.class.getEnumConstants();
        List<CommentStatusVo> goodsStatusVos=new ArrayList<CommentStatusVo>();
        for(int i=0;i<commentStatuses.length;i++){
            goodsStatusVos.add(new CommentStatusVo(commentStatuses[i]));
        }
        logger.debug(goodsStatusVos.toString());
        return ResponseUtil.ok(new ReturnObject<List>(goodsStatusVos).getData());
    }

    /**
     * 管理员审核评论
     * createdBy TGF 2020/12/13 20：30
     * @param shopId 店铺id
     * @param id 评论id
     * @param state 操作字段（是否审核通过）
     * @return Object 操作信息
     */
    @ApiOperation(value = "管理员审核评论", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "token", required = true),
            @ApiImplicitParam(paramType="path", dataType="int", name="shopId", value="店铺id", required = true),
            @ApiImplicitParam(paramType="path", dataType="int", name="id", value="订单id", required = true),
            @ApiImplicitParam(paramType="body", dataType="boolean", name="message", value="操作字段（是否审核通过）", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @PutMapping("/shops/{did}/comments/{id}/confirm")
    public Object alertMessage(@PathVariable Long shopId,
                               @PathVariable Long id,
                               @Validated @RequestBody boolean state) {
        logger.debug("alertMessage: shopId = " + id + ", state = " + state);
        ReturnObject retObject = commentService.alertMessage(id, state);
        logger.debug("Errno: " + retObject.getCode().toString() + " Errmsg: " + retObject.getErrmsg());
        return Common.getNullRetObj(new ReturnObject<>(retObject.getCode(), retObject.getErrmsg()), httpServletResponse);
    }

    /**
     * 买家查看自己的评价记录
     * createdBy TGF 2020/12/13 21：15
     * @param page 页码
     * @param pageSize 每页数目
     * @param userId 用户id
     * @return ReturnObject 评价记录
     */
    @ApiOperation(value = "买家查看自己的评价记录", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "token", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "page", value = "页码"),
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "pageSize", value = "页大小"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @Audit
    @GetMapping("comments")
    public Object findOrders(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize,
            @LoginUser @ApiIgnore @RequestParam(required = false) Long userId){

        Object object;

        if(userId == null){
            object = Common.getNullRetObj(new ReturnObject<>(ResponseCode.AUTH_NEED_LOGIN), httpServletResponse);
        }
        else{
            if(page <= 0 || pageSize <= 0)
                object = Common.getNullRetObj(new ReturnObject<>(ResponseCode.FIELD_NOTVALID), httpServletResponse);
            else{
                ReturnObject<PageInfo<VoObject>> retObj = commentService.findComments(userId, page, pageSize);
                object = Common.getPageRetObject(retObj);
            }
        }
        return object;
    }
}
