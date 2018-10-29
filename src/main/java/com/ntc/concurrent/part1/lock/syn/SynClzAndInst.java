package com.ntc.concurrent.part1.lock.syn;

import com.ntc.concurrent.util.tool.SleepTools;
import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>对象锁和类</h2>
 * <ul>
 * <li>对象锁：锁的是Class对象new出来的实例,锁同一个实例，线程会等待，锁不同的实例，线程不会等待</li>
 * <li>类锁：锁的是每个类的Class对象，与对象锁不相互不影响</li>
 * </ul>
 * @author Michael-Chen
 */
public class SynClzAndInst {
	
	//使用类锁的线程
    private static class SynClassThread extends Thread{
    	
    	public SynClassThread(String name) {
			super(name);
		}
    	
        @Override
        public void run() {
            System.out.println(LogTool.time() + this.getName() + " is running...");
            // 类锁
            synClass();
        }
    }

    //使用对象锁的线程
    private static class InstanceSynThread implements Runnable{
    	// 定义对象
        private SynClzAndInst synClzAndInst;
        
        public InstanceSynThread(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }

        public void run() {
            System.out.println(LogTool.time() + Thread.currentThread().getName() + " is running... " + synClzAndInst);
            //对象锁
            synClzAndInst.instance();
        }
    }

    //使用对象锁的线程
    private static class Instance2SynThread implements Runnable{
        private SynClzAndInst synClzAndInst;

        public Instance2SynThread(SynClzAndInst synClzAndInst) {
            this.synClzAndInst = synClzAndInst;
        }
        
        public void run() {
            System.out.println(LogTool.time() + Thread.currentThread().getName() + " is running... " + synClzAndInst);
            //对象锁
            synClzAndInst.instance2();
        }
    }

    //锁对象
    private synchronized void instance(){
        SleepTools.ms(50);
        System.out.println(LogTool.time() + Thread.currentThread().getName() + " is going... " + this.toString());
        
        SleepTools.ms(50);
        System.out.println(LogTool.time() + Thread.currentThread().getName() +" ended " + this.toString());
    }
    
    //锁对象
    private synchronized void instance2(){
    	SleepTools.ms(50);
        System.out.println(LogTool.time() + Thread.currentThread().getName() + " is going... " + this.toString());
        
        SleepTools.ms(50);
        System.out.println(LogTool.time() + Thread.currentThread().getName() + " ended " + this.toString());
    }

    //类锁，实际是锁类的class对象
    private static synchronized void synClass(){
    	SleepTools.ms(50);
        System.out.println(LogTool.time() + Thread.currentThread().getName() + " going... ");
        
        SleepTools.ms(50);
        System.out.println(LogTool.time() + Thread.currentThread().getName() + " end " + LogTool.newLine());
    }

    /**
     * @return
     * <pre>
     *[ Time = 1540826968654 ] ====> instanceSynThread start. 
[ Time = 1540826968654 ] ====> instanceSynThread2 start. 
[ Time = 1540826968654 ] ====> instanceSynThread3 start. 
                               
[ Time = 1540826968654 ] ====> instanceSynThread is running... com.ntc.concurrent.part1.lock.syn.SynClzAndInst@3661fa09
[ Time = 1540826968654 ] ====> instance2SynThread is running... com.ntc.concurrent.part1.lock.syn.SynClzAndInst@26484d1e
[ Time = 1540826968654 ] ====> instanceSynThread3 is running... com.ntc.concurrent.part1.lock.syn.SynClzAndInst@3661fa09

[ Time = 1540826968655 ] ====> synClassThread start. 
[ Time = 1540826968655 ] ====> synClassThread is running...
[ Time = 1540826968705 ] ====> synClassThread going... 
[ Time = 1540826968705 ] ====> instance2SynThread is going... com.ntc.concurrent.part1.lock.syn.SynClzAndInst@26484d1e
[ Time = 1540826968705 ] ====> instanceSynThread is going... com.ntc.concurrent.part1.lock.syn.SynClzAndInst@3661fa09
[ Time = 1540826968755 ] ====> instance2SynThread ended com.ntc.concurrent.part1.lock.syn.SynClzAndInst@26484d1e
[ Time = 1540826968755 ] ====> instanceSynThread ended com.ntc.concurrent.part1.lock.syn.SynClzAndInst@3661fa09
[ Time = 1540826968755 ] ====> synClassThread end 
                               
[ Time = 1540826968805 ] ====> instanceSynThread3 is going... com.ntc.concurrent.part1.lock.syn.SynClzAndInst@3661fa09
[ Time = 1540826968855 ] ====> instanceSynThread3 ended com.ntc.concurrent.part1.lock.syn.SynClzAndInst@3661fa09
     * </pre>
     * @param args
     */
    public static void main(String[] args) {
    	// new 出对象实例
    	SynClzAndInst synClzAndInst = new SynClzAndInst();
    	// 对象锁线程
    	Thread instanceSynThread = new Thread(new InstanceSynThread(synClzAndInst));
    	
    	SynClzAndInst synClzAndInst2 = new SynClzAndInst();
    	Thread instance2SynThread = new Thread(new Instance2SynThread(synClzAndInst2));
    	
    	Thread instanceSynThread3 = new Thread(new InstanceSynThread(synClzAndInst));
    	
    	System.out.println(LogTool.time() + "instanceSynThread start. ");
    	instanceSynThread.setName("instanceSynThread");
    	instanceSynThread.start();
    	
    	System.out.println(LogTool.time() + "instanceSynThread2 start. ");
    	instance2SynThread.setName("instance2SynThread");
    	instance2SynThread.start();
    	
    	System.out.println(LogTool.time() + "instanceSynThread3 start. " + LogTool.newLine());
    	instanceSynThread3.setName("instanceSynThread3");
    	instanceSynThread3.start();
    	
    	// 类锁线程
    	SynClassThread synClassThread = new SynClassThread("synClassThread");
    	System.out.println();
    	System.out.println(LogTool.time() + synClassThread.getName() + " start. ");
    	synClassThread.start();
    	
    	SleepTools.ms(1000);
    }
}
