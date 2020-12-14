package cn.edu.xmu.g12demomybatis.model;

import java.time.LocalDateTime;
import java.util.List;

public class SuperJoin {
    private Integer id;
    private String username;
    private LocalDateTime createTime;
    private String password;
    private List<Orders> orders;
    private Detail detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "SuperJoin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", createTime=" + createTime +
                ", password='" + password + '\'' +
                ", orders=" + orders +
                ", detail=" + detail +
                "}\n";
    }
}
