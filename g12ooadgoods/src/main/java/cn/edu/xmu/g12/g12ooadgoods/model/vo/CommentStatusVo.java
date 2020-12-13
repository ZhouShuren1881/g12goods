package cn.edu.xmu.g12.g12ooadgoods.model.vo;

import cn.edu.xmu.g12.g12ooadgoods.util.CommentStatus;

public class CommentStatusVo {
    private Long Code;

    private String name;
    public CommentStatusVo(CommentStatus status){
        Code=Long.valueOf(status.getCode());
        name=status.getDescription();
    }
}
