package com.ntc.concurrent.part6.mypool;


import java.util.Random;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 测试自定义线程池
 * @author Michael-Chen
 */
public class TestMyThreadPool {
	
	/**
	 * <pre>
	 *[ Time = 1543070639825 ] ====> WorkThread number: 3  wait task number: 2
[ Time = 1543070639825 ] ====> 12 ready exec : com.ntc.concurrent.part6.mypool.TestMyThreadPool$MyTask@a1a1c96
[ Time = 1543070639825 ] ====> 11 ready exec : com.ntc.concurrent.part6.mypool.TestMyThreadPool$MyTask@6894b612
[ Time = 1543070639825 ] ====> 10 ready exec : com.ntc.concurrent.part6.mypool.TestMyThreadPool$MyTask@2aa87dcf
[ Time = 1543070642081 ] ====> 任务  testC 完成
[ Time = 1543070642081 ] ====> 12 ready exec : com.ntc.concurrent.part6.mypool.TestMyThreadPool$MyTask@4dde3a5
[ Time = 1543070642301 ] ====> 任务  testB 完成
[ Time = 1543070642301 ] ====> 11 ready exec : com.ntc.concurrent.part6.mypool.TestMyThreadPool$MyTask@66535570
[ Time = 1543070642703 ] ====> 任务  testA 完成
[ Time = 1543070644325 ] ====> 任务  testE 完成
[ Time = 1543070644532 ] ====> 任务  testD 完成
[ Time = 1543070649826 ] ====>  ready close pool.....
[ Time = 1543070649826 ] ====> WorkThread number: 3  wait task number: 0

	 * </pre>
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main(String[] args) throws InterruptedException {
        // 创建3个线程的线程池
        MyThreadPool t = new MyThreadPool(3,0);
        t.execute(new MyTask("testA"));
        t.execute(new MyTask("testB"));
        t.execute(new MyTask("testC"));
        t.execute(new MyTask("testD"));
        t.execute(new MyTask("testE"));
        System.out.println(t);
        Thread.sleep(10000);
        t.destroy();// 所有线程都执行完成才destory
        System.out.println(t);
    }

    // 任务类
    static class MyTask implements Runnable {

        private String name;
        private Random r = new Random();

        public MyTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {// 执行任务
            try {
                Thread.sleep(r.nextInt(1000)+2000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getId()+" sleep InterruptedException:"
                        +Thread.currentThread().isInterrupted());
            }
            System.out.println(LogTool.time() + "任务  " + name + " 完成");
        }
    }
}

