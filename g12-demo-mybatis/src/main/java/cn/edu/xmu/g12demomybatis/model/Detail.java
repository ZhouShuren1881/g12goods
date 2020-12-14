package cn.edu.xmu.g12demomybatis.model;

public class Detail {
    private Integer id;
    private String Avatar;
    private String Addr;
    private Integer customerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", Avatar='" + Avatar + '\'' +
                ", Addr='" + Addr + '\'' +
                ", customerId=" + customerId +
                '}';
    }
}
