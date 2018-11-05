package com.ntc.concurrent.part2.tools;

import java.util.concurrent.CountDownLatch;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;

/**
 * <h2>CountDownLatch</h2>
 * <ul>
 * <li>CountDownLatch是一组线程等待其他线程完成工作后执行（和CyclicBarrier的主要区别），类似加强版join</li>
 * <li>CountDownLatch需要定于扣除点个数，当某线程使用await()方法时，需要等待预定的扣除点都被扣除完，线程才会继续执行。</li>
 * <li>一个线程可以使用使用多个countDown(),以消耗扣除点</li>
 * <li>CountDownLatch放行由第三者控制,放行条件》=线程数</li>
 * <li>应用场景：启动某个服务前，需要先启动若干个外部服务</li>
 * </ul>
 * @author Michael-Chen
 */
public class UseCountDownLatch {
	
	// 定义countDownLatch类，参数为满足释放条件的扣除点个数
	static CountDownLatch latch = new CountDownLatch(6);
	
	// 初始化线程
	private static class  InitThread implements Runnable {
		String threadName;
		
		public InitThread(String string) {
			this.threadName = string;
		}
		public void run() {
			System.out.println(LogTool.time() + "Thread_"+threadName
					+ " countDown one.");
			latch.countDown();
			
			System.out.println(LogTool.time() + "Thread_"+threadName
					+ " countDown two.");
			latch.countDown();
			
			System.out.println(LogTool.time() + "Thread_"+threadName
					+ " countDown three.");
			latch.countDown();
		}
	}
	
	// 业务线程
	private static class BusiThread implements Runnable {
		public void run() {
			try {
				System.out.println(LogTool.time() + "BusiThread is waiting latch.");
				// 等待countDownLatch满足条件
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(LogTool.time() + "BusiThread do business.");
		}
	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 * <pre>
	 *[ Time = 1541344645592 ] ====> The args of CountDownLatch is 6.

[ Time = 1541344645592 ] ====> Call InitThread_1 to start.
[ Time = 1541344645593 ] ====> Call BusiThread to start.
[ Time = 1541344645593 ] ====> Thread_InitThread_1 countDown one.
[ Time = 1541344645593 ] ====> Thread_InitThread_1 countDown two.
[ Time = 1541344645593 ] ====> Thread_InitThread_1 countDown three.
[ Time = 1541344645594 ] ====> BusiThread is waiting latch.

[ Time = 1541344647595 ] ====> Call InitThread_2 to start.
[ Time = 1541344647595 ] ====> MainThread is waiting latch.
[ Time = 1541344647595 ] ====> Thread_InitThread_2 countDown one.
[ Time = 1541344647596 ] ====> Thread_InitThread_2 countDown two.
[ Time = 1541344647596 ] ====> Thread_InitThread_2 countDown three.
[ Time = 1541344647596 ] ====> BusiThread do business.
[ Time = 1541344647596 ] ====> MainThread do ites work........
	 * </pre>
	 */
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println(LogTool.time() + "The args of CountDownLatch is 6.");
		System.out.println("");
		// 启动初始化线程1
		System.out.println(LogTool.time() + "Call InitThread_1 to start.");
		new Thread(new InitThread("InitThread_1")).start();
		
		// 业务线程启动
		System.out.println(LogTool.time() + "Call BusiThread to start.");
		new Thread(new BusiThread()).start();
		
		SleepTools.second(2);
		System.out.println("");
		
		// 启动初始化线程2
		System.out.println(LogTool.time() + "Call InitThread_2 to start.");
		new Thread(new InitThread("InitThread_2")).start();
		
		// 主线程等待countDownLatch满足条件
		System.out.println(LogTool.time() + "MainThread is waiting latch.");
		latch.await();
        System.out.println(LogTool.time() + "MainThread do ites work........");
	}
}
