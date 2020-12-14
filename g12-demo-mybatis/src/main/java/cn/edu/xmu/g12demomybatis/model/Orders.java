package cn.edu.xmu.g12demomybatis.model;

public class Orders {
    private Integer id;
    private Integer amount;
    private Integer customerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", amount=" + amount +
                ", customerId=" + customerId +
                '}';
    }
}
