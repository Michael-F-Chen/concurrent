package com.ntc.concurrent.part6.comps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示CompletionService异步获取并行任务执行结果
 * CompletionService让先完成任务的结果，先拿到（拿到结果是有序的，单个批次内（cpu核心数的线程个数）执行时间少的在前面）
 * 自己实现的线程池，只能根据线程加入的顺序拿到执行结果（拿到结果是无效的）
 * @author Michael-Chen
 */
public class CompletionCase {
    private final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK = Runtime.getRuntime().availableProcessors();

    // 方法一，自己写集合来实现获取线程池中任务的返回结果
    public void testByQueue() throws Exception {
    	long start = System.currentTimeMillis();
    	
    	//统计所有任务休眠的总时长
    	AtomicInteger count = new AtomicInteger(0);
    	
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        
        //容器存放提交给线程池的任务,list,map,
        BlockingQueue<Future<Integer>> queue = 
        		new LinkedBlockingQueue<Future<Integer>>();

        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
            Future<Integer> future = pool.submit(new WorkTask("ExecTask" + i));
            queue.add(future);//i=0 先进队列，i=1的任务跟着进
        }

        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
        	int sleptTime = queue.take().get();///i=0先取到，i=1的后取到
        	System.out.println(" slept "+sleptTime+" ms ...");        	
        	count.addAndGet(sleptTime);
        }

        // 关闭线程池
        pool.shutdown();
        System.out.println("-------------tasks sleep time "+count.get()
        		+"ms,and spend time "
        		+(System.currentTimeMillis()-start)+" ms");
    }

    // 方法二，通过CompletionService来实现获取线程池中任务的返回结果
    public void testByCompletion() throws Exception {
    	long start = System.currentTimeMillis();
    	AtomicInteger count = new AtomicInteger(0);
    	
        // 创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        CompletionService<Integer> cService = new ExecutorCompletionService<>(pool);
        
        // 向里面扔任务
        for (int i = 0; i < TOTAL_TASK; i++) {
        	cService.submit(new WorkTask("ExecTask" + i));
        }
        
        // 检查线程池任务执行结果
        for (int i = 0; i < TOTAL_TASK; i++) {
        	int sleptTime = cService.take().get();
        	System.out.println(" slept "+sleptTime+" ms ...");        	
        	count.addAndGet(sleptTime);
        }        

        // 关闭线程池
        pool.shutdown();
        System.out.println("-------------tasks sleep time "+count.get()
			+"ms,and spend time "
			+(System.currentTimeMillis()-start)+" ms");
    }

    /**
     * <pre>
     * slept 434 ms ...
 slept 764 ms ...
 slept 716 ms ...
 slept 869 ms ...
-------------tasks sleep time 2783ms,and spend time 924 ms
 slept 386 ms ...
 slept 554 ms ...
 slept 578 ms ...
 slept 616 ms ...
-------------tasks sleep time 2134ms,and spend time 619 ms
     * </pre>
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CompletionCase t = new CompletionCase();
        t.testByQueue();
        t.testByCompletion();
    }
}
