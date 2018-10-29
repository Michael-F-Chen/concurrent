package com.ntc.concurrent.part1.thread;

import java.util.concurrent.ExecutionException;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>安全的停止线程</h2>
 * <p>java是协作式的，不到万不得已不可以用.stop()停止线程</p>
 * <ol>
 *		<li>
 *			继承Tread类的线程，在线程外使用【thread.interrupt()】方法将线程停止标志位设置为true，<br>
 * 			并且线程内部需要使用【isInterrupted()】方法对线程标志位进行判断，并根据不同的值进行处理，<br>
 * 			<b>不处理，线程是不会理会自动停下的。</b>
 * 		</li>
 *  	<li>
 *			实现Runnable接口的类，不能在线程外直接使用使用【thread.interrupt()】，<br>
 *			需要先用Thread类包装，在调用【thread.interrupt()】，<br>
 *			<b>interrupt()是Tread类的方法。</b>
 * 		</li>
 * </ol>
 * @author Michael-Chen
 */
public class EndThread {


	/**
	 * 继承Thread类
	 * <ol>
	 *		<li>【thread.interrupt()】：在线程<em> 外部 </em>将停止标志位设置成true,是否会停止，线程内部判断。</li>
	 *		<li>【isInterrupted()】：在线程<em> 内部 </em>判断停止标志位，并做响应处理。</li>
	 * </ol>
	 * @author Michael-Chen
	 */
	private static class UseThread extends Thread {
		
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			
			// 根据线程外部设置的 endThread.interrupt() 来判断处理是否停止本线程。
			while (!isInterrupted()) {
			
			// 如果不理会interrupted(),线程是不会停止的。
			//while (true) {
				System.out.println(LogTool.time() + threadName + " is running.");
			}
			
			System.out.println(LogTool.time() + threadName + " interrupt falg is " + isInterrupted());
		}
		
	}
	
	/**
	 * 实现Runnable接口的类，需要从当前线程类中获取isInterrupted()
	 * 
	 * @author Michael-Chen
	 */
	private static class UseRun implements Runnable {

		public void run() {
			String threadName = Thread.currentThread().getName();
			
			// 根据线程外部设置的 endThread.interrupt() 来判断处理是否停止本线程。
			while (!Thread.currentThread().isInterrupted()) {
			
			// 如果不理会interrupted(),线程是不会停止的。
			//while (true) {
				System.out.println(LogTool.time() + threadName + " is running.");
			}
			
			System.out.println(LogTool.time() +threadName + " interrupt falg is " + Thread.currentThread().isInterrupted());
		}

	}
	
	/**
	 * @return
	 * [ 2018-10-27 23:24:45:461 ] ====> start endTread. <br>
	 * [ 2018-10-27 23:24:45:463 ] ====> endTread is running. <br>
	 * [ 2018-10-27 23:24:45:463 ] ====> start useRunThread. <br>
	 * [ 2018-10-27 23:24:45:463 ] ====> endTread is running. <br>
	 * [ 2018-10-27 23:24:45:463 ] ====> useRunThread is running. <br>
	 * [ 2018-10-27 23:24:45:463 ] ====> mainTread sleep 1 milliseconds begin. <br>
	 * [ 2018-10-27 23:24:45:464 ] ====> useRunThread is running. <br>
	 * [ 2018-10-27 23:24:45:464 ] ====> endTread is running. <br>
	 * [ 2018-10-27 23:24:45:464 ] ====> useRunThread is running. <br>
	 * [ 2018-10-27 23:24:45:464 ] ====> endTread is running. <br>
	 * [ 2018-10-27 23:24:45:465 ] ====> useRunThread is running. <br>
	 * [ 2018-10-27 23:24:45:465 ] ====> mainTread sleep 1 milliseconds end. <br>
	 * [ 2018-10-27 23:24:45:465 ] ====> *** Tread interrupt *** <br>
	 * [ 2018-10-27 23:24:45:465 ] ====> useRunThread is running. <br>
	 * [ 2018-10-27 23:24:45:465 ] ====> endTread is running. <br>
	 * [ 2018-10-27 23:24:45:465 ] ====> useRunThread interrupt falg is true <br>
	 * [ 2018-10-27 23:24:45:466 ] ====> endTread interrupt falg is true <br>
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Thread endThread = new UseThread("endTread");
		System.out.println(LogTool.time() + "start endTread.");
		endThread.start();
		
		// 实现runable接口的方法，需要使用Thread类包装
		UseRun useRun = new UseRun();
		Thread useRunThread = new Thread(useRun,"useRunThread");
		System.out.println(LogTool.time() + "start useRunThread.");
		useRunThread.start();
		
		System.out.println(LogTool.time() + "mainTread sleep 1 milliseconds begin.");
		Thread.sleep(1);
		System.out.println(LogTool.time() + "mainTread sleep 1 milliseconds end.");
		
		System.out.println(LogTool.time() + "*** Tread interrupt ***");
		endThread.interrupt();
		useRunThread.interrupt();
		
	}
}
