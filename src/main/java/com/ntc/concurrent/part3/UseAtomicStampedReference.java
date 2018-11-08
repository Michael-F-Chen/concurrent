package com.ntc.concurrent.part3;

import java.util.concurrent.atomic.AtomicStampedReference;

import com.ntc.concurrent.util.tool.LogTool;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：演示带版本戳的原子操作类
 */
/**
 * <h2>演示带版本戳的原子操作类</h2>
 * <ul>
 * <li>用来解决CAS的ABA问题（A->B->A，同样是A，不一定是原始的A，使用版本号来解决）</li>
 * <li>解决ABA问题有两个类【AtomicMarkableReference】和【AtomicStampedReference】</li>
 * <li>AtomicMarkableReference 返回boolean，只关心数据是否被动过（是否是原始的数据）</li>
 * <li>AtomicStampedReference 返回的是次数，关心数据被动过几次</li>
 * </ul>
 * @author Michael-Chen
 */
public class UseAtomicStampedReference {
	
	static AtomicStampedReference<String> asr = 
			new AtomicStampedReference<String>("Michael",0);
	

	/**
	 * @param args
	 * @throws InterruptedException
	 * <pre>
	 *[ Time = 1541604297692 ] ====> 初始版本数据： Michael初始版本戳： 0
[ Time = 1541604297694 ] ====> Thread-0 当前变量值： Michael 当前版本戳： 0 修改的目标版本戳为：0 此次更新是否成功：true
[ Time = 1541604297694 ] ====> Thread-1 当前变量值： MichaelJava 当前版本戳： 1 修改的目标版本戳为：0 此次更新是否成功：false
[ Time = 1541604297694 ] ====>  最终变量值： MichaelJava 当最终版本戳： 1
	 * </pre>
	 */
    public static void main(String[] args) throws InterruptedException {
    	// 拿初始的版本号
    	final int oldStamp = asr.getStamp();
    	// 拿初始的数据
    	final String oldReferenc = asr.getReference();
    	
    	System.out.println(LogTool.time() + "初始版本数据： " + oldReferenc
    			+ "初始版本戳： " + oldStamp );
    	
    	
    	// 第一个线程
    	Thread rightStampThread = new Thread(new Runnable() {

			public void run() {
				String threadName = Thread.currentThread().getName();
				System.out.println(LogTool.time() + threadName  
						+ " 当前变量值： " + asr.getReference() + " 当前版本戳： " + asr.getStamp() 
						+ " 修改的目标版本戳为：" + oldStamp 
						+ " 此次更新是否成功：" + asr.compareAndSet(oldReferenc, oldReferenc+"Java", oldStamp, oldStamp+1));
			}
    		
    	});
    	
    	// 第二个线程
    	Thread errorStampThread = new Thread(new Runnable() {

			public void run() {
				String threadName = Thread.currentThread().getName();
				String reference = asr.getReference();
				System.out.println(LogTool.time() + threadName  
						+ " 当前变量值： " + reference + " 当前版本戳： " + asr.getStamp() 
						+ " 修改的目标版本戳为：" + oldStamp 
						+ " 此次更新是否成功：" + asr.compareAndSet(reference, reference+"C", oldStamp, oldStamp+1));
			}
    		
    	});   	
    	
    	// 启动正确版本戳的线程
    	rightStampThread.start();
    	rightStampThread.join();
    	
    	// 启动错误版本戳的线程
    	errorStampThread.start();
    	errorStampThread.join();
    	
    	System.out.println(LogTool.time() + " 最终变量值： " + asr.getReference() + " 当最终版本戳： " + asr.getStamp());
    	
    }
}
