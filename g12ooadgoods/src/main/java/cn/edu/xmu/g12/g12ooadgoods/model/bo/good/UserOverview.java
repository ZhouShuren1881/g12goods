package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import cn.edu.xmu.g12.g12ooadgoods.mapper.AuthUserPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.po.AuthUserPo;
import lombok.Data;

@Data
public class UserOverview {
    private Long id;
    private String userName;

    public UserOverview() { }

    public UserOverview(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public UserOverview(AuthUserPo po) {
        this(po.getId(), po.getUserName());
    }
}