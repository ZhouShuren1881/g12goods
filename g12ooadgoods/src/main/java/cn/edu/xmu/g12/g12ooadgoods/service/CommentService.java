package cn.edu.xmu.g12.g12ooadgoods.service;

import cn.edu.xmu.g12.g12ooadgoods.dao.CommentDao;
import cn.edu.xmu.g12.g12ooadgoods.model.VoObject;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.CommentInformation;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    /**
     * 管理员审核评论
     * createdBy TGF 2020/12/06 21：00
     * @param id 评论id
     * @param state 操作字段（是否审核通过）
     * @return Object 操作信息
     */
    @Transactional
    public ReturnObject alertMessage(Long id, Object state){
        ReturnObject retObj = commentDao.updateState(id, state);
        return retObj;
    }

    /**
     * 买家查询自己的评价记录
     * @author TGF
     * @created 2020/12/13 21：20
     * @param customerId 买家id
     * @return ReturnObject<CommentsVo> 查询结果
     */
    public ReturnObject<PageInfo<VoObject>> findComments(Long customerId, Integer page, Integer pageSize){

        PageHelper.startPage(page, pageSize);
        PageInfo<CommentPo> commentsPo = commentDao.selectMyComments(customerId);
        List<VoObject> comments = commentsPo.getList().stream().map(OrderBriefInformation::new).filter(CommentInformation::authetic).collect(Collectors.toList());

        PageInfo<VoObject> retObj = new PageInfo<>(comments);
        retObj.setPageNum(commentsPo.getPages());
        retObj.setPageNum(commentsPo.getPageNum());
        retObj.setPageSize(commentsPo.getPageSize());
        retObj.setTotal(commentsPo.getTotal());

        return new ReturnObject<>(retObj);
    }
}
