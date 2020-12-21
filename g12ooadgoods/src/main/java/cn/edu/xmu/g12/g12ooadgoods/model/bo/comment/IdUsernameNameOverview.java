package cn.edu.xmu.g12.g12ooadgoods.model.bo.comment;

import cn.edu.xmu.g12.g12ooadgoods.model.po.AuthUserPo;
import lombok.Data;

@Data
public class IdUsernameNameOverview {
    private Long id;
    private String userName;
    private String name;

    public IdUsernameNameOverview(AuthUserPo po) {
        id = po.getId();
        userName = po.getUserName();
        name = po.getName();
    }
}
