package com.ntc.concurrent.part5.bq;

import java.util.concurrent.DelayQueue;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 将订单放入队列
 * @author Michael-Chen
 */
public class PutOrder implements Runnable {
	
	private DelayQueue<ItemVo<Order>> queue;
	
	public PutOrder(DelayQueue<ItemVo<Order>> queue) {
		super();
		this.queue = queue;
	}

	public void run() {
		//5秒到期
		Order ordeTb = new Order("Tb12345",366);
		ItemVo<Order> itemTb = new ItemVo<Order>(5000, ordeTb);
		queue.offer(itemTb);
		System.out.println(LogTool.time() + "订单5秒后到期：" + ordeTb.getOrderNo());
		
		//8秒到期
		Order ordeJd = new Order("Jd54321",366);
		ItemVo<Order> itemJd = new ItemVo<Order>(8000, ordeJd);
		queue.offer(itemJd);
		System.out.println(LogTool.time() + "订单8秒后到期："+ordeJd.getOrderNo());		
	}	
}
