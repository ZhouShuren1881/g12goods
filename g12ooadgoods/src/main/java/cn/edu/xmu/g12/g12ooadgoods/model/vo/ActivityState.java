package cn.edu.xmu.g12.g12ooadgoods.model.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActivityState {
    private Integer code;
    private String name;

    public ActivityState() {}

    ActivityState(int state) {
        this();
        switch (state) {
            case 0:
                code = 0;
                name = "已下线";
                break;
            case 1:
                code = 1;
                name = "已上线";
                break;
            default:
                code = 2;
                name = "已删除";
                break;
        }
    }

    public static List<ActivityState> getAllStates() {
        var s = new ArrayList<ActivityState>();
        for (int i = 0; i <= 2; i++) s.add(new ActivityState(i));
        return s;
    }
}
