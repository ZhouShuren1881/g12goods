package cn.edu.xmu.g12.g12ooadgoods.controller;

import cn.edu.xmu.g12.g12ooadgoods.dao.CommentDao;
import cn.edu.xmu.g12.g12ooadgoods.model.vo.comment.NewCommentVo;
import cn.edu.xmu.g12.g12ooadgoods.util.Common;
import cn.edu.xmu.g12.g12ooadgoods.util.JwtHelper;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CommentController {

    private  static  final Logger logger = LoggerFactory.getLogger(CommentController.class);

    private JwtHelper jwt;

    @Autowired
    CommentDao commentDao;

    public CommentController() {
        jwt = new JwtHelper();
    }

    @GetMapping("/comments/states")
    public Object getAllStates() {
        return commentDao.getAllStates();
    }
    //public List<CommentState> getAllStates() {
    //        return CommentState.getAllStates();
    //    }

    @PostMapping("/orderitems/{orderItemId}/comments")
    public Object newSkuComment(@PathVariable Long orderItemId, @Validated @RequestBody NewCommentVo vo,
                                BindingResult bindingResult,
                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("newSkuComment controller orderItemId="+orderItemId+" NewCommentVo="+vo.toString());
        /* 处理参数校验错误 */
        Object object = Common.processFieldErrors(bindingResult, response);
        if(object != null) return object;

        var userAndDepart = jwt.verifyTokenAndGetClaims(request.getHeader("authorization"));


//        var returnObject = commentDao.newSkuComment(orderItemId, )
        return null;
    }
    //public ReturnObject<CommentBo> newSkuComment(Long orderId, Long userId, NewCommentVo vo) {

    @GetMapping("/skus/{skuId}/comments")
    public Object getSkuCommentValid() { return null; }
    //public ReturnObject<ListBo<CommentBo>> getSkuCommentValid(Long skuId,
    //                                                @Nullable Integer page, @Nullable Integer pageSize) {

    @PutMapping("/shops/{did}/comments/{id}/confirm")
    public Object confirmComment() { return null; }
    //public ResponseCode confirmComment(Long shopId, Long commentId, ConfirmCommentVo vo)

    @GetMapping("/comments")
    public Object getCommentOfUser() { return null; }
    //public ReturnObject<ListBo<CommentBo>> getCommentOfUser(Long userId,
    //                                                            @Nullable Integer page, @Nullable Integer pageSize) {

    @GetMapping("/shops/{id}/comments/all")
    public Object getSkuCommentByAdmin() { return null; }
    //public ReturnObject<ListBo<CommentBo>> getSkuCommentByAdmin(Long skuId, Long shopId, @Nullable Byte state,
    //                                                              @Nullable Integer page, @Nullable Integer pageSize)


}
