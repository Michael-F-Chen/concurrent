package com.ntc.concurrent.part1.lock.vola;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;

/**
 * <h2>volatile</h2>
 * <ul>
 * <li>volatile无法提供操作的原子性，只能确保可见性</li>
 * <li>volatile是线程非安全的，只合适在一个线程中使用</li>
 * <li>volatile最常用的场景：一个线程写，多个线程读</li>
 * <li>volatile在多线程写入的情况下，无法保证正确性</li>
 * </ul>
 * @author Michael-Chen
 */
public class VolatileUnsafe {
	
	private static class VolatileVar implements Runnable {

//		private int a = 0;
		private volatile int a = 0;
		
		/**
		 * 使用synchronized关键字，运行结果如下
		 *<pre>
		 *[ Time = 1540908024593 ] ====> Thread-0 === 1
[ Time = 1540908024694 ] ====> Thread-0 === 2
[ Time = 1540908024694 ] ====> Thread-3 === 3
[ Time = 1540908024794 ] ====> Thread-3 === 4
[ Time = 1540908024794 ] ====> Thread-2 === 5
[ Time = 1540908024894 ] ====> Thread-2 === 6
[ Time = 1540908024894 ] ====> Thread-1 === 7
[ Time = 1540908024994 ] ====> Thread-1 === 8
		 *</pre>
		 */
//		public synchronized void run() {
		public void run() {
			String threadName = Thread.currentThread().getName();
			a = a + 1;
			System.out.println(LogTool.time() + threadName + " === " + a);
			SleepTools.ms(100);
			a = a + 1;
			System.out.println(LogTool.time() + threadName + " === " + a);
		}
		
	}
	
	
	/**
	 * 使用volatile关键字，结果无序
	 * <pre>
	 *[ Time = 1540910067236 ] ====> Thread-3 === 4
[ Time = 1540910067236 ] ====> Thread-1 === 4
[ Time = 1540910067236 ] ====> Thread-2 === 4
[ Time = 1540910067236 ] ====> Thread-0 === 4
[ Time = 1540910067337 ] ====> Thread-1 === 5
[ Time = 1540910067337 ] ====> Thread-2 === 7
[ Time = 1540910067337 ] ====> Thread-0 === 7
[ Time = 1540910067337 ] ====> Thread-3 === 8

	 * </pre>
	 * @param args
	 */
	public static void main(String[] args) {
		
		VolatileVar v = new VolatileVar();
		
		Thread t1 = new Thread(v);
		Thread t2 = new Thread(v);
		Thread t3 = new Thread(v);
		Thread t4 = new Thread(v);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
	}

}
