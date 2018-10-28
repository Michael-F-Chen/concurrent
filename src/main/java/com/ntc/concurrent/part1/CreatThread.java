package com.ntc.concurrent.part1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.ntc.concurrent.util.Tool;

/**
 * <h2>创建线程的几种方式</h2>
 * <ul>
 * 		<li>继承 Thread类 （java是单继承的，所以需要有以下两个接口）</li>
 * 		<li>实现 Runnable接口（无返回值，可以直接交给Thread执行）</li>
 *  	<li>实现 Callable接口 （有返回值，需要FutureTask类包装交给Thread类执行）</li>
 * </ul>
 * @author Michael-Chen
 */
public class CreatThread {

	/**
	 * 实现Runnable接口的类，线程执行没有返回值
	 * 
	 * @author Michael-Chen
	 */
	private static class UseRun implements Runnable {

		public void run() {
			System.out.println(Tool.nowTime("实现Runnable的线程"));
		}

	}

	/**
	 * 实现Callable接口的类，线程执行有返回值
	 * @author Michael-Chen
	 */
	private static class UseCall implements Callable<String> {

		public String call() throws Exception {
			System.out.println(Tool.nowTime("实现Callable的线程"));
			return "CallResult";
		}

	}

	/**
	 * 继承Thread类，线程执行没有返回值
	 * @author Michael-Chen
	 */
	private static class UseThread extends Thread {
		
		public void start() {
			System.out.println(Tool.nowTime("继承自Thread的线程"));
		}
	}
	
	/**
	 * @return
	 * <p> [ 2018-10-25 22:47:41:875 ] ====> 继承自Thread的线程 </p>
	 * <p> [ 2018-10-25 22:47:41:875 ] ====> 实现Runnable的线程 </p>
	 * <p> [ 2018-10-25 22:47:41:875 ] ====> 实现Callable的线程 </p>
	 * <p> 获取Callable的返回值： CallResult </p>
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		UseThread useThread = new UseThread();
		useThread.start();
		
		UseRun useRun = new UseRun();
		// 交接Thread类，运行定义的线程实例
		new Thread(useRun).start();

		UseCall useCall = new UseCall();
		// 实现Callable接口的类不可以直接交给Thread类运行
		FutureTask<String> futureTask = new FutureTask<String>(useCall);
		// 需要先使用FutureTask类包装，再将FutureTask实例交给Thread类运行
		new Thread(futureTask).start();
		// 返回结果也需要使用FutureTask的实例获取
		System.out.println("获取Callable的返回值： " + futureTask.get());
	
	}
}
