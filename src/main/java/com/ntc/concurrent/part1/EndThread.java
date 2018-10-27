package com.ntc.concurrent.part1;

import java.util.concurrent.ExecutionException;

import com.ntc.concurrent.util.Tool;

/**
 * 安全的停止线程
 * <p>java是写协作式的，不到万不得已不可以用.stop()停止线程</p>
 * 
 * @author Michael-Chen
 */
public class EndThread {


	/**
	 * 继承Thread类，线程执行没有返回值
	 * @author Michael-Chen
	 */
	private static class UseThread extends Thread {
		
		public UseThread(String name) {
			super(name);
		}
		
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			
			// 当线程中断标志为不为true
			while (!isInterrupted()) {
				System.out.println(Tool.nowTime(threadName + " is running."));
			}
			
			System.out.println(Tool.nowTime(threadName + " interrupt falg is " + isInterrupted()));
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		Thread endThread = new UseThread("endTread");
		System.out.println(Tool.nowTime("start endTread."));
		endThread.start();
		
		System.out.println(Tool.nowTime("mainTread sleep 20 milliseconds begin."));
		Thread.sleep(20);
		System.out.println(Tool.nowTime("mainTread sleep 20 milliseconds end."));
		
		System.out.println(Tool.nowTime("endTread interrupt"));
		endThread.interrupt();
		
	}
}
