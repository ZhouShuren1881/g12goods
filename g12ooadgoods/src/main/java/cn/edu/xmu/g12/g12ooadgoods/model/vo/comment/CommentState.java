package cn.edu.xmu.g12.g12ooadgoods.model.vo.comment;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentState {
    private Integer code;
    private String name;

    public CommentState() {}

    CommentState(int state) {
        this();
        switch (state) {
            case 0:
                code = 0;
                name = "未审核";
                break;
            case 1:
                code = 1;
                name = "评论成功";
                break;
            default:
                code = 2;
                name = "未通过";
                break;
        }
    }

    public static List<CommentState> getAllStates() {
        var s = new ArrayList<CommentState>();
        for (int i = 0; i <= 2; i++) s.add(new CommentState(i));
        return s;
    }
}
