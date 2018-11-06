package com.ntc.concurrent.part2.semaphore;

import java.sql.Connection;
import java.util.Random;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;

/**
 * <h2>演示Semaphore</h2>
 * <ul>
 * <li>控制同时访问某个特定资源的线程数量，用在流量控制</li>
 * <li>应用场景：启动多个线程读取文件后，存入数据库，但是数据库连接有限</li>
 * </ul>
 * @author Michael-Chen
 */
public class TestSemaphore {

	private static DBPoolSemaphore dbPool = new DBPoolSemaphore();
	
	// 业务线程
	private static class BusiThread extends Thread{
		@Override
		public void run() {
			// 让每个线程持有连接的时间不一样
			Random r = new Random();
			long start = System.currentTimeMillis();
			
			try {
				Connection connect = dbPool.takeConnect();
				System.out.println(LogTool.time() + "Thread_"+Thread.currentThread().getId()
						+ " 获取数据库连接共耗时【"+(System.currentTimeMillis()-start)+"】ms.");
				
				SleepTools.ms(100+r.nextInt(100));//模拟业务操作，线程持有连接查询数据
				
				System.out.println(LogTool.time() + "Thread_"+Thread.currentThread().getId()
						+ " 查询数据完成，归还连接！");
				dbPool.returnConnect(connect);
			} catch (InterruptedException e) {
			}
		}
	}
	
	/**
	 * @param args
	 * @return
	 * <pre>
	 *[ Time = 1541430442662 ] ====> Thread_10 获取数据库连接共耗时【0】ms.
[ Time = 1541430442662 ] ====> Thread_11 获取数据库连接共耗时【0】ms.
[ Time = 1541430442766 ] ====> Thread_11 查询数据完成，归还连接！
[ Time = 1541430442766 ] ====> 当前有【4】个线程等待数据库连接！！ 可用连接数: 0
[ Time = 1541430442766 ] ====> Thread_13 获取数据库连接共耗时【104】ms.
[ Time = 1541430442834 ] ====> Thread_10 查询数据完成，归还连接！
[ Time = 1541430442834 ] ====> 当前有【3】个线程等待数据库连接！！ 可用连接数: 0
[ Time = 1541430442834 ] ====> Thread_15 获取数据库连接共耗时【172】ms.
[ Time = 1541430442903 ] ====> Thread_13 查询数据完成，归还连接！
[ Time = 1541430442903 ] ====> 当前有【2】个线程等待数据库连接！！ 可用连接数: 0
[ Time = 1541430442903 ] ====> Thread_12 获取数据库连接共耗时【241】ms.
[ Time = 1541430442947 ] ====> Thread_15 查询数据完成，归还连接！
[ Time = 1541430442947 ] ====> 当前有【1】个线程等待数据库连接！！ 可用连接数: 0
[ Time = 1541430442947 ] ====> Thread_14 获取数据库连接共耗时【285】ms.
[ Time = 1541430443019 ] ====> Thread_12 查询数据完成，归还连接！
[ Time = 1541430443019 ] ====> 当前有【0】个线程等待数据库连接！！ 可用连接数: 0
[ Time = 1541430443089 ] ====> Thread_14 查询数据完成，归还连接！
[ Time = 1541430443089 ] ====> 当前有【0】个线程等待数据库连接！！ 可用连接数: 1

	 * </pre>
	 */
	public static void main(String[] args) {
		// 启动业务线程
        for (int i = 0; i < 6; i++) {
            Thread thread = new BusiThread();
            thread.start();
        }
	}
	
}
