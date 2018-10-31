package com.ntc.concurrent.part1.lock.threadLocal;



import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>ThreadLocal</h2>
 * <ul>
 * <li>ThreadLocal为线程变量。可以理解为是个map，类型 Map<Thread,Integer></li>
 * <li>保证每个线程之间变量互不影响</li>
 * <li>一般用于连接池</li>
 * </ul>
 * @author Michael-Chen
 */
public class UseThreadLocal {

	// 给ThreadLocal设置初始值
	static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
		
		// 初始值为 1
		protected Integer initialValue() {
			return 1;
		}
	};
	
	/**
	 * 运行3个线程,默认值累加
	 */
	public void StartThreadArray() {
		Thread[] runs = new Thread[3];
		for (int i = 0; i < runs.length; i++) {
			runs[i] = new Thread(new TestThread(i));
		}
		for (int i = 0; i < runs.length; i++) {
			runs[i].start();
		}
	}
	
	/**
	 * 将ThreadLocal变量的值变化，并写回，看线程之间是否会互相影响
	 * @author Michael-Chen
	 */
	public static class TestThread implements Runnable {
		int id;
		public TestThread(int id) {
			this.id = id;
		}
		public void run() {
			System.out.println(LogTool.time() + Thread.currentThread().getName() + " : start");
			Integer s = threadLocal.get();
			s = s + id;
			threadLocal.set(s);
			System.out.println(LogTool.time() + Thread.currentThread().getName() + " " + threadLocal.get());
		}
	}
	
	/**
	 * 将ThreadLocal变量的值变化，并写回，看线程之间是否会互相影响
	 * @return
	 * <pre>
	 *[ Time = 1540911002345 ] ====> Thread-2 : start
[ Time = 1540911002345 ] ====> Thread-0 : start
[ Time = 1540911002345 ] ====> Thread-1 : start
[ Time = 1540911002345 ] ====> Thread-0 1
[ Time = 1540911002345 ] ====> Thread-2 3
[ Time = 1540911002345 ] ====> Thread-1 2

	 * </pre>
	 * @param args
	 */
	public static void main(String[] args) {
		UseThreadLocal test = new UseThreadLocal();
		test.StartThreadArray();
	}
}
