package com.ntc.concurrent.part1;

import java.util.concurrent.ExecutionException;

import com.ntc.concurrent.util.Tool;

/**
 * <h2>InterruptedException异常 </h2>
 * <ul>
 * 		<li>抛出【InterruptedException】异常时，线程的 isInterrupted() 标志位会被复位为 【false】</li>
 * 		<li>处理方式为在 【catch】 中手动中断此线程（调用 interrupt() 方法）</li>
 * 		<li>一般外部调用interrupt()方法时，线程内部处于sleep状态，会造成此异常</li>
 * </ul>
 * @author Michael-Chen
 */
public class HasInterrputException {


	/**
	 * 抛出【InterruptedException】异常时，线程的 isInterrupted() 标志位会被复位为 【false】，<br>
	 * 处理方式为在 【catch】 中手动中断此线程（调用 interrupt() 方法）。
	 * 
	 * @author Michael-Chen
	 */
	private static class UseThread extends Thread {
		
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			
			while (!isInterrupted()) {
				System.out.println(Tool.nowTime(threadName + " is running."));
				try {
					System.out.println(Tool.nowTime("HasInterrputEx sleep 100 milliseconds start."));
					Thread.sleep(100);
					System.out.println(Tool.nowTime("HasInterrputEx sleep 100 milliseconds end."));
				} catch (InterruptedException e) {
					System.out.println(Tool.nowTime("[InterruptedException] " + threadName + " interrupt falg is " + isInterrupted()));
					
					System.out.println(Tool.nowTime("[InterruptedException] use interrupt()"));
					// InterruptedException异常，会将停止标志位复位为 false，需手动停止此线程
					interrupt();
					e.printStackTrace();
				}
			}
			
			// 不在catch中调用interrupt()，这里不会执行
			System.out.println(Tool.nowTime(threadName + " interrupt falg is " + isInterrupted()));
		}
		
	}
	
	/**
	 * @return
	 * [ 2018-10-27 23:11:27:283 ] ====> start HasInterrputEx. <br>
	 * [ 2018-10-27 23:11:27:283 ] ====> mainTread sleep 1 milliseconds begin. <br>
	 * [ 2018-10-27 23:11:27:283 ] ====> HasInterrputEx is running. <br>
	 * [ 2018-10-27 23:11:27:284 ] ====> HasInterrputEx sleep 100 milliseconds start. <br>
	 * [ 2018-10-27 23:11:27:284 ] ====> mainTread sleep 1 milliseconds end. <br>
	 * [ 2018-10-27 23:11:27:284 ] ====> *** HasInterrputEx interrupt *** <br>
	 * [ 2018-10-27 23:11:27:284 ] ====> [InterruptedException] HasInterrputEx interrupt falg is false <br>
	 * [ 2018-10-27 23:11:27:284 ] ====> [InterruptedException] use interrupt() <br>
	 * java.lang.InterruptedException: sleep interrupted <br>
	 * [ 2018-10-27 23:11:27:285 ] ====> HasInterrputEx interrupt falg is true <br>
	 *	   at java.lang.Thread.sleep(Native Method) <br>
	 *	   at com.ntc.concurrent.part1.HasInterrputException$UseThread.run(HasInterrputException.java:31) <br>
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Thread endThread = new UseThread("HasInterrputEx");
		System.out.println(Tool.nowTime("start HasInterrputEx."));
		endThread.start();
		
		System.out.println(Tool.nowTime("mainTread sleep 1 milliseconds begin."));
		Thread.sleep(1);
		System.out.println(Tool.nowTime("mainTread sleep 1 milliseconds end."));
		
		System.out.println(Tool.nowTime("*** HasInterrputEx interrupt ***"));
		endThread.interrupt();
	}
}
