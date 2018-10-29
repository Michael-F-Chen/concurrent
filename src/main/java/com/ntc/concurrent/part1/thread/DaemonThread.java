package com.ntc.concurrent.part1.thread;

import java.util.concurrent.ExecutionException;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>守护线程 </h2>
 * <p>和主线程不一定同生，但是一定共死的线程</p>
 * <ul>
 * 		<li>设置守护线程，线程内的finally语句不一定会被执行</li>
 * </ul>
 * @author Michael-Chen
 */
public class DaemonThread {

	private static class UseThread extends Thread {

		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			try {
				while (!isInterrupted()) {
					System.out.println(LogTool.time() + threadName 
							+ " I am extends Thread.");
				} 
			} finally {
				System.out.println(LogTool.time() + threadName + " === finally");
			}
			
		}
		
	}
	
	/**
	 * 
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @return
	 * <pre>[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.
[ Time = 1540822390206 ] ====> Thread-0 I am extends Thread.</pre>
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Thread useThread = new UseThread();
		
		// 设置守护线程， 线程中的finally 不一定会被执行
		useThread.setDaemon(true);
		useThread.start();
		Thread.sleep(2);
		
//		useThread.interrupt();
		// 将useThread.setDaemon(true);注释掉执行此句，会打印
		// [ Time = 1540822666636 ] ====> Thread-0 === finally
	}
}
