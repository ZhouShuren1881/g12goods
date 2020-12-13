package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.model.vo.CommentStatusVo;
import cn.edu.xmu.g12.g12ooadgoods.service.CommentService;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import cn.edu.xmu.g12.g12ooadgoods.util.CommentStatus;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;

@Api(value = "评论服务", tags = "comment")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/comment", produces = "application/json;charset=UTF-8")
public class CommentController {
    private  static  final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;




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
}
