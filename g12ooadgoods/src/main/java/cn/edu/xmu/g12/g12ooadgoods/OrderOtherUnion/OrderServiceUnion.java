package cn.edu.xmu.g12.g12ooadgoods.OrderOtherUnion;

import cn.edu.xmu.g12.g12ooadgoods.util.ReturnObject;
import cn.edu.xmu.oomall.order.model.OrderDTO;
import cn.edu.xmu.oomall.order.service.IOrderService;
import com.alibaba.nacos.api.annotation.NacosInjected;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceUnion {
    @NacosInjected
    IOrderService orderService;
    public ReturnObject<OrderDTO> getUserSelectSOrderInfo(Long userId, Long orderItemId) {
        return new Trans<OrderDTO>().ooad(orderService.getUserSelectSOrderInfo(userId, orderItemId));
    }

    public ReturnObject<OrderDTO> getShopSelectOrderInfo(Long shopId, Long orderItemId) {
        return new Trans<OrderDTO>().ooad(orderService.getShopSelectOrderInfo(shopId, orderItemId));
    }
}
