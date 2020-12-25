package cn.edu.xmu.oomall.order.discount;

import cn.edu.xmu.oomall.order.bo.OrderItem;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-18
 */
public interface Computable {

	List<OrderItem> compute(List<OrderItem> orderItems);
}
