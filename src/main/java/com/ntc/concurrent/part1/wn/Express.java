package com.ntc.concurrent.part1.wn;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 快递实体类
 * 用于测试wait()、notify()、notifyAll()
 * @author Michael-Chen
 * @see com.ntc.concurrent.part1.wn.TestWN.main(String[])
 */
public class Express {

	public final static String CITY = "ShangHai";
	// 快递运输的里程数
	private int km;
	// 快递到达地点
	private String site;
	
	public Express(int km, String site) {
		this.km = km;
		this.site = site;
	}

	/**
	 * 改变快递运输的距离，通知其他线程
	 */
	public synchronized void changeKm() {
		this.km = 101;
		System.out.println(LogTool.time() + " The km changed.");
		notifyAll();
//		notify();
	}
	
	/**
	 * 改变快递所在的城市，通知其他线程
	 */
	public synchronized void changeSite() {
		this.site = "BeiJing";
		notifyAll();
	}
	
	/**
	 *  等待距离发生变化后，进行业务操作
	 */
	public synchronized void waitKm() {
		while (this.km <= 100) {
			try {
				System.out.println(LogTool.time() + " The check km thread " + LogTool.tips(Thread.currentThread().getId()+"") + " is waitting to be notifed");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(LogTool.time() + " The km is " + this.km + ", i will change the DB." );
	}
	
	/**
	 * 等待城市发生变化后，进行业务操作
	 */
	public synchronized void waitSite() {
		while (CITY.equals(this.site)) {
			try {
				System.out.println(LogTool.time() + " The check site thread " + LogTool.tips(Thread.currentThread().getId()+"") + " is waitting to be notifed");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(LogTool.time() + " The site is " + this.site + ", i will call the user." );
	}
}
