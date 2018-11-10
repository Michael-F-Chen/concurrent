package com.ntc.concurrent.part4.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 快递类
 * @author Michael-Chen
 */
public class ExpressCond {
    public final static String CITY = "ShangHai";
    private int km;/*快递运输里程数*/
    private String site;/*快递到达地点*/
    // 因为有两个成员变量，所有定义两个锁（一个lock也可以对应多个Condition）可重入锁
    private Lock kmLock = new ReentrantLock();
    private Lock siteLock = new ReentrantLock();
    
    private Condition keCond = kmLock.newCondition();
    private Condition siteCond = siteLock.newCondition();

    public ExpressCond() {
    }

    public ExpressCond(int km, String site) {
        this.km = km;
        this.site = site;
    }

    // 变化公里数，然后通知处于wait状态并需要处理公里数的线程进行业务处理
    public void changeKm(){
    	kmLock.lock();
        try {
        	this.km = 101;
        	// 当使用多个newCondition时尽量用signal() 而不用signalAll()
        	// 和notiyAll() 的区别在在于，用wait()方法等待，使用notiy()可能会被别的wait()拦截
        	// Condition的await()方法，是精确的知道是哪个对象在await(),不用担心别其他await()拦截
//        	keCond.signalAll();
        	keCond.signal();
        }finally {
        	kmLock.unlock();
        }
    }

    // 变化地点，然后通知处于wait状态并需要处理地点的线程进行业务处理
    public  void changeSite(){
    	siteLock.lock();
        try {
        	this.site = "BeiJing";
        	siteCond.signal();
        }finally {
        	siteLock.unlock();
        }    	
    }

    // 当快递的里程数大于100时更新数据库
    public void waitKm(){
    	kmLock.lock();
    	try {
        	while(this.km <= 100) {
        		try {
        			keCond.await();
        			
    				System.out.println(LogTool.time() + "check km thread["+Thread.currentThread().getId()
    						+"] is be notifed.");
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	}    		
    	}finally {
    		kmLock.unlock();
    	}

        System.out.println(LogTool.time() +"the Km is "+this.km+",I will change db");
    }

    // 当快递到达目的地时通知用户
    public void waitSite(){
    	siteLock.lock();
        try {
        	while(CITY.equals(this.site)) {
        		try {
        			siteCond.await();
    				System.out.println(LogTool.time() +"check site thread["+Thread.currentThread().getId()
    						+"] is be notifed.");
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
        	}
        }finally {
        	siteLock.unlock();
        } 
        System.out.println(LogTool.time() +"the site is "+this.site+",I will call user");
    }
}
