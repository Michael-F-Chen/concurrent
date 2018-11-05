package com.ntc.concurrent.part2.tools;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>CyclicBarrier的使用</h2>
 * <ul>
 * <li>让一组线程达到某个屏障，被阻塞，直到组内最后一个线程达到屏障时，屏障开放，所有被阻塞的线程会继续运行（和CountDownLatch的主要区别）</li>
 * <li>CyclicBarrier放行由一组线程本身控制，放行条件=线程数</li>
 * <li>应用场景：多线程处理excel，当所有线程处理完毕后，再进行数据汇总</li>
 * </ul>
 * @author Michael-Chen
 */
public class UseCyclicBarrier {
	
	// 定义CyclicBarrier实例，参数为内部满足条件的数量，和满足条件后，所需要执行的线程
	private static CyclicBarrier barrier 
		= new CyclicBarrier(5, new CollectThread());
	
	// 存放每个线程的容器
    private static ConcurrentHashMap<String,Long> resultMap
            = new ConcurrentHashMap<String, Long>();//存放子线程工作结果的容器

    // 负责屏障开放以后的工作
    private static class CollectThread implements Runnable{

        public void run() {
        	System.out.println();
            StringBuilder result = new StringBuilder();
            for(Map.Entry<String,Long> workResult : resultMap.entrySet()){
            	result.append("["+workResult.getValue()+"]");
            }
            System.out.println(LogTool.time() + "The result = "+ result);
            System.out.println(LogTool.time() + "do other business........");
            System.out.println();
        }
    }

    // 工作线程
    private static class SubThread implements Runnable{

        public void run() {
        	long id = Thread.currentThread().getId();//线程本身的处理结果
            resultMap.put(Thread.currentThread().getId()+"", id);
            Random r = new Random();//随机决定工作线程的是否睡眠
            try {
                if(r.nextBoolean()) {
                	Thread.sleep(2000+id);
                	System.out.println(LogTool.time() + "Thread_"+id+" ....do something ");
                }
                System.out.println(LogTool.time() + "Thread_"+id + "....is await");
                // 此处是核心，当线程本身到达barrier栅栏时，需要等待，直到同一组其他线程都运行到等待的位置，线程才可以继续执行
                barrier.await();	
                
            	Thread.sleep(1000+id);
                System.out.println(LogTool.time() + "Thread_"+id+" ....do its business ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * @param args
     * @return
     * <pre>
     *[ Time = 1541427839298 ] ====> Thread_13....is await
[ Time = 1541427839298 ] ====> Thread_12....is await
[ Time = 1541427839298 ] ====> Thread_11....is await
[ Time = 1541427841308 ] ====> Thread_10 ....do something 
[ Time = 1541427841308 ] ====> Thread_10....is await
[ Time = 1541427841312 ] ====> Thread_14 ....do something 
[ Time = 1541427841312 ] ====> Thread_14....is await

[ Time = 1541427841314 ] ====> The result = [11][12][13][14][10]
[ Time = 1541427841314 ] ====> do other business........

[ Time = 1541427842324 ] ====> Thread_10 ....do its business 
[ Time = 1541427842325 ] ====> Thread_11 ....do its business 
[ Time = 1541427842326 ] ====> Thread_12 ....do its business 
[ Time = 1541427842327 ] ====> Thread_13 ....do its business 
[ Time = 1541427842328 ] ====> Thread_14 ....do its business 

     * </pre>
     */
    public static void main(String[] args) {
    	// 循环启动5个线程
        for(int i=0 ; i<=4; i++){
            Thread thread = new Thread(new SubThread());
            thread.start();
        }

    }
}
