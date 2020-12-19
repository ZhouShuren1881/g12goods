package cn.edu.xmu.g12.g12ooadgoods.model.vo.good;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GoodState {

    private Integer code;
    private String name;

    public GoodState() {}
    GoodState(int state) {
        this();
        switch (state) {
            case 0:
                code = 0;
                name = "未上架";
                break;
            case 4:
                code = 4;
                name = "上架";
                break;
            default:
                code = 6;
                name = "已删除";
                break;
        }
    }

    public static List<GoodState> getAllStates() {
        var s = new ArrayList<GoodState>();
        s.add(new GoodState(0));
        s.add(new GoodState(4));
        s.add(new GoodState(6));
        return s;
    }
}
