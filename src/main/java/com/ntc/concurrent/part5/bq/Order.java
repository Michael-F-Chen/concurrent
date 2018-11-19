package com.ntc.concurrent.part5.bq;

/**
 * 订单的实体类
 * @author Michael-Chen
 */
public class Order {
	// 订单的编号
	private final String orderNo;
	// 订单的金额
	private final double orderMoney;
	
	public Order(String orderNo, double orderMoney) {
		super();
		this.orderNo = orderNo;
		this.orderMoney = orderMoney;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public double getOrderMoney() {
		return orderMoney;
	}
	
	
	
}
