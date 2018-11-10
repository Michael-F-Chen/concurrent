package com.ntc.concurrent.part4.condition;

/**
 * 测试Lock和Condition实现等待通知
 * @author Michael-Chen
 */
public class TestCond {
	
    private static ExpressCond express = new ExpressCond(0,ExpressCond.CITY);

    /*检查里程数变化的线程,不满足条件，线程一直等待*/
    private static class CheckKm extends Thread{
        @Override
        public void run() {
        	express.waitKm();
        }
    }

    /*检查地点变化的线程,不满足条件，线程一直等待*/
    private static class CheckSite extends Thread{
        @Override
        public void run() {
        	express.waitSite();
        }
    }

    /**
     * @param args
     * @throws InterruptedException
     * <pre>
     *使用signalAll()会让所有对应的等待线程获得消息
[ Time = 1541771810190 ] ====> check km thread[13] is be notifed.
[ Time = 1541771810191 ] ====> the Km is 101,I will change db
[ Time = 1541771810191 ] ====> check km thread[14] is be notifed.
[ Time = 1541771810191 ] ====> the Km is 101,I will change db
[ Time = 1541771810191 ] ====> check km thread[15] is be notifed.
[ Time = 1541771810191 ] ====> the Km is 101,I will change db

使用signal()只会让获得锁的等待线程获得消息
[ Time = 1541771944262 ] ====> check km thread[13] is be notifed.
[ Time = 1541771944262 ] ====> the Km is 101,I will change db
     * </pre>
     */
    public static void main(String[] args) throws InterruptedException {
        // 等待地点变化的线程
    	for(int i=0;i<3;i++){
            new CheckSite().start();
        }
    	
    	// 等待公里数变化的线程
        for(int i=0;i<3;i++){
            new CheckKm().start();
        }

        Thread.sleep(1000);
        // 公里数发生变化
        express.changeKm();
    }
}
