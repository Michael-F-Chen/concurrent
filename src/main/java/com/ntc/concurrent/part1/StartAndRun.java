package com.ntc.concurrent.part1;

import com.ntc.concurrent.util.Tool;

/**
 * <h2>开始线程</h2>
 * <p>star()和run()的区别</p>
 * <ul>
 *		<li>
 *			 run()是调用对象的普通方法
 * 		</li>
 *  	<li>
 *			start()会将线程对象和操作系统中实际的线程进行映射，再执行run()方法
 * 		</li>
 * </ul>
 * @author Michael-Chen
 */
public class StartAndRun {

	private static class ThreadRun extends Thread {
		
		@Override
		public void run() {
			
			int i = 3;
			while (i > 0) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Tool.time() + "I am " + Tool.tips(Thread.currentThread().getName()));
				i--;
			}
		}
		
	}
	
	
	
	/**
	 * @return
	 * [ Time = 1540819428615 ] ====> I am [ main ] <br>
	 * [ Time = 1540819429115 ] ====> I am [ main ] <br>
	 * [ Time = 1540819429615 ] ====> I am [ main ] <br>
	 * [ Time = 1540819430115 ] ====> I am [ BeCalledThread ] <br>
	 * [ Time = 1540819430615 ] ====> I am [ BeCalledThread ] <br>
	 * [ Time = 1540819431115 ] ====> I am [ BeCalledThread ] <br>
	 * @param args
	 */
	public static void main(String[] args) {

		ThreadRun beCalledThread = new ThreadRun();
		beCalledThread.setName("BeCalledThread");
		
		// 调用对象的普通方法 run() 
		// [ Time = 1540733930202 ] ====> I am [ main ]
		beCalledThread.run();	 
		
		// 调用start()方法后，会将线程对象和操作系统中实际的线程进行映射，再执行run()方法
		//  [ Time = 1540819431115 ] ====> I am [ BeCalledThread ]
		beCalledThread.start();	
		
	}
}
