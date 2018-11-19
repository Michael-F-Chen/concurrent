package com.ntc.concurrent.part5.bq;

import java.util.concurrent.DelayQueue;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 取出到期订单的功能
 * @author Michael-Chen
 */
public class FetchOrder implements Runnable {
	
	private DelayQueue<ItemVo<Order>> queue;
	
	public FetchOrder(DelayQueue<ItemVo<Order>> queue) {
		super();
		this.queue = queue;
	}	

	public void run() {
		while(true) {
			try {
				ItemVo<Order> item = queue.take();
				Order order = (Order)item.getDate();
				System.out.println(LogTool.time() + "get from queue:"+order.getOrderNo());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
}
