package cn.edu.xmu.g12.g12ooadgoods.model.bo.good;

import lombok.Data;

@Data
public class IdNameOverview {
    private Long id;
    private String name;

    public IdNameOverview() { }

    public IdNameOverview(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
