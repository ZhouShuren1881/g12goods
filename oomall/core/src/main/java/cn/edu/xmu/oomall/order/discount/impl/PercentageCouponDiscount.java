package cn.edu.xmu.oomall.order.discount.impl;

import cn.edu.xmu.oomall.order.bo.OrderItem;
import cn.edu.xmu.oomall.order.discount.BaseCouponDiscount;
import cn.edu.xmu.oomall.order.discount.BaseCouponLimitation;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
public class PercentageCouponDiscount extends BaseCouponDiscount {

	public PercentageCouponDiscount(){}

	public PercentageCouponDiscount(BaseCouponLimitation limitation, long value) {
		super(limitation, value);
	}

	@Override
	public void calcAndSetDiscount(List<OrderItem> orderItems) {
		for (OrderItem oi : orderItems) {
			oi.setDiscount(oi.getPrice() - value / 100 * oi.getPrice());
		}
	}
}
