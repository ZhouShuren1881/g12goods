package cn.edu.xmu.g12.g12ooadgoods.OrderOtherImpl;

import cn.edu.xmu.g12.g12ooadgoods.mapper.OrderItemPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.mapper.OrdersPoMapper;
import cn.edu.xmu.ooad.util.ResponseCode;
import cn.edu.xmu.ooad.util.ReturnObject;
import cn.edu.xmu.oomall.order.model.OrderDTO;
import cn.edu.xmu.oomall.order.model.OrderInnerDTO;
import cn.edu.xmu.oomall.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired(required = false)
    OrderItemPoMapper orderItemPoMapper;
    @Autowired(required = false)
    OrdersPoMapper ordersPoMapper;

    /**
     * 根据orderItemId查询订单详情表和订单表信息，同时验证该orderItem是否属于该用户
     */
    public ReturnObject<OrderDTO> getUserSelectSOrderInfo(Long userId, Long orderItemId) {
        var orderItemPo = orderItemPoMapper.selectByPrimaryKey(orderItemId);
        if (orderItemPo == null) return new ReturnObject<>(ResponseCode.RESOURCE_ID_NOTEXIST);

        var ordersPo = ordersPoMapper.selectByPrimaryKey(orderItemPo.getOrderId());
        if (!ordersPo.getCustomerId().equals(userId)) return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE);

        return new ReturnObject<>(new OrderDTO(ordersPo.getId(), ordersPo.getOrderSn(),
                orderItemPo.getGoodsSkuId(), orderItemPo.getName(), ordersPo.getShopId()));
    }
    /**
     * 根据orderItemId查询订单详情表和订单表信息，同时验证该orderItem是否属于该商店
     * shopId为0时表示管理员 无需验证
     */
    public ReturnObject<OrderDTO> getShopSelectOrderInfo(Long shopId, Long orderItemId) {
        var orderItemPo = orderItemPoMapper.selectByPrimaryKey(orderItemId);
        var ordersPo = ordersPoMapper.selectByPrimaryKey(orderItemPo.getOrderId());
        if (!ordersPo.getShopId().equals(shopId)) return new ReturnObject<>(ResponseCode.RESOURCE_ID_OUTSCOPE);

        return new ReturnObject<>(new OrderDTO(ordersPo.getId(), ordersPo.getOrderSn(),
                orderItemPo.getGoodsSkuId(), orderItemPo.getName(), ordersPo.getShopId()));
    }
    /**
     * 根据orderItemIdList查询订单详情表和订单表信息，同时验证该orderItem是否属于该店铺，返回orderItemId为key的Map
     * shopId为0时表示管理员 无需验证
     */
    public ReturnObject<Map<Long,OrderDTO>> getShopSelectOrderInfoByList(Long shopId, List<Long> orderItemIdList) {
        return null;
    }
    /**
     * 根据orderItemIdList查询订单详情表和订单表信息，同时验证该orderItem是否属于该用户，返回orderItemId为key的Map
     */
    public ReturnObject<Map<Long,OrderDTO>> getUserSelectOrderInfoByList(Long userId, List<Long> orderItemIdList) {
        return null;
    }
    /**
     * 根据userId查询该用户的订单详情表，并根据skuId的list筛选orderItemId，返回orderItemId的List
     */
    public ReturnObject<List<Long>> listUserSelectOrderItemIdBySkuList(Long userId, List<Long> skuId) {
        return null;
    }
    /**
     * 根据shopId查询该商铺的所有订单详情表，并根据skuId的list筛选orderItemId，返回orderItemId的List
     */
    public ReturnObject<List<Long>> listAdminSelectOrderItemIdBySkuList(Long shopId, List<Long> skuId) {
        return null;
    }
    /**
     * 换货api，产生orderitemId的新订单，商品数量为quantity
     */
    public ReturnObject<Long> getAdminHandleExchange(Long userId, Long shopId, Long orderItemId, Integer quantity, Long aftersaleId) {
        return null;
    }

    /**
     * 由商品模块调用 下线团购时，通知订单模块将团购订单转为普通订单
     */
    public ReturnObject<Object> putGrouponOffshelves(Long grouponId) {
        return null;
    }

    /**
     * 由商品模块调用 下线预售时，通知订单模块对预售订单进行操作
     */
    public ReturnObject<Object> putPresaleOffshevles(Long presaleId) {
        return null;
    }

    /**
     * 由商品模块调用 支付完拆单
     */
    public ReturnObject<ResponseCode> splitOrders(Long orderId) {
        return null;
    }

    /**
     * 由商品模块调用：团购活动结束，商品模块调用此接口，订单用于退团购优惠金额
     */
    public ReturnObject<Object> grouponEnd(String strategy, Long GrouponId) {
        return null;
    }

    /**
     * 通过订单id查找用户id
     */
    public ReturnObject<OrderInnerDTO> findUserIdbyOrderId(Long orderId) {
        return null;
    }

    /**
     * 通过订单id查找shopId
     */
    public ReturnObject<OrderInnerDTO> findShopIdbyOrderId(Long orderId) {
        return null;
    }

    /**
     * 通过订单详情id查找订单id
     */
    public ReturnObject<OrderInnerDTO> findOrderIdbyOrderItemId(Long orderItemId) {
        return null;
    }

    /**
     * 判断订单是否属于某个商铺
     */
    public ReturnObject isOrderBelongToShop(Long shopId, Long orderId) {
        return null;
    }

    /**
     * 通过OrderItemId获取OrderId
     */
    public ReturnObject<Long> getOrderIdByOrderItemId(Long orderId) {
        return null;
    }
}
