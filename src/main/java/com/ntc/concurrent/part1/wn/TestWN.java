package com.ntc.concurrent.part1.wn;

/**
 * 演示notify()和notifyAll()的区别
 * @author Michael-Chen
 */
public class TestWN {
	
	private static Express express = new Express(0, Express.CITY);
	
	/**
	 * 定义check距离的线程
	 * @author Michael-Chen
	 */
	private static class CheckKm extends Thread {
		@Override
		public void run() {
			express.waitKm();
		}
	}
	
	/**
	 * 定义check城市的线程
	 * @author Michael-Chen
	 */
	private static class CheckSite extends Thread {
		@Override
		public void run() {
			express.waitSite();
		}
	}
	
	/**
	 * 
	 * <pre>
	 * 当waitKm()方法里使用了notify(),会通知对象上等待的线程栈中第一个线程，如果此线程不是期望的线程，会造成通知信号丢失
	 *[ Time = 1540998715639 ] ====>  The check site thread [ 10 ]  is waitting to be notifed
	 *
	 * 当waitKm()方法里使用了notifyAll(),会通知对象上等待的线程栈中所有线程
	 *[ Time = 1540999054317 ] ====>  The check site thread [ 10 ]  is waitting to be notifed
[ Time = 1540999054317 ] ====>  The check km thread [ 13 ]  is waitting to be notifed
[ Time = 1540999054318 ] ====>  The check km thread [ 12 ]  is waitting to be notifed
[ Time = 1540999054318 ] ====>  The check site thread [ 11 ]  is waitting to be notifed
[ Time = 1540999055817 ] ====>  The km changed.
[ Time = 1540999055817 ] ====>  The check site thread [ 11 ]  is waitting to be notifed
[ Time = 1540999055817 ] ====>  The km is 101, i will change the DB.
[ Time = 1540999055817 ] ====>  The km is 101, i will change the DB.
[ Time = 1540999055817 ] ====>  The check site thread [ 10 ]  is waitting to be notifed
</pre>
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// 启动2个线程，check快递位置
		for (int i = 0; i < 2; i++) {
			new CheckSite().start();
		}
		// 启动2个线程，check快递运输的距离
		for (int i = 0; i < 2; i++) {
			new CheckKm().start();
		}
		
		Thread.sleep(1500);
		
		// 快递运输的距离发生变化
		express.changeKm();
		
	}
}
