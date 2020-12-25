package cn.edu.xmu.g12.g12ooadgoods.model.bo.comment;

import cn.edu.xmu.g12.g12ooadgoods.model.po.AuthUserPo;
import cn.edu.xmu.oomall.other.model.CustomerDTO;
import lombok.Data;

@Data
public class IdUsernameNameOverview {
    private Long id;
    private String userName;
    private String name;

    public IdUsernameNameOverview(Long userId, CustomerDTO dto) {
        id = userId;
        userName = dto.getUserName();
        name = dto.getName();
    }
}
