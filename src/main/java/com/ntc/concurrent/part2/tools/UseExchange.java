package com.ntc.concurrent.part2.tools;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Exchanger;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;

/**
 * <h2>Exchange的使用</h2>
 * <ul>
 * <li>仅限于两个线程之间的数据交换</li>
 * <li>应用场景较少</li>
 * </ul>
 * 
 * @author Michael-Chen
 */
public class UseExchange {

	private static final Exchanger<Set<String>> exchange = new Exchanger<Set<String>>();

	/**
	 * @param args
	 * @return
	 * <pre>
	 *[ Time = 1541511636213 ] ====>  setA add a.
[ Time = 1541511636213 ] ====>  setB add b.
[ Time = 1541511636214 ] ====>  setB is watting to exchange data.
[ Time = 1541511638215 ] ====>  setA is watting to exchange data.
[ Time = 1541511638215 ] ====>  setB : a
[ Time = 1541511638215 ] ====>  setA : b
	 * </pre>
	 */
	public static void main(String[] args) {
		// 第一个线程
		new Thread(new Runnable() {
			public void run() {
				Set<String> setA = new HashSet<String>();// 存放数据的容器
				try {
					System.out.println(LogTool.time() + " setA add a." );
					setA.add("a");
					
					SleepTools.second(2);
					
					// 等待交换数据，会在此处阻塞
					System.out.println(LogTool.time() + " setA is watting to exchange data." );
					setA = exchange.exchange(setA);
					
					// 处理交换后的数据 
					for (Iterator<String> iterator = setA.iterator(); iterator.hasNext();) {
						String string = (String) iterator.next();
						System.out.println(LogTool.time() + " setA : " + string);
					}
				} catch (InterruptedException e) {
				}
			}
		}).start();

		// 第二个线程
		new Thread(new Runnable() {
			public void run() {
				Set<String> setB = new HashSet<String>();// 存放数据的容器
				try {
					System.out.println(LogTool.time() + " setB add b." );
					setB.add("b");
					// 等待交换数据，会在此处阻塞
					System.out.println(LogTool.time() + " setB is watting to exchange data." );
					setB = exchange.exchange(setB);
					
					// 处理交换后的数据 
					for (Iterator<String> iterator = setB.iterator(); iterator.hasNext();) {
						String string = (String) iterator.next();
						System.out.println(LogTool.time() + " setB : " + string);
					}
				} catch (InterruptedException e) {
				}
			}
		}).start();

	}
}
