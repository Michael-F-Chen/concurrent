package com.ntc.concurrent.part6.mypool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 线程池的自定义实现
 * @author Michael-Chen
 */
public class MyThreadPool {
	
    // 线程池中默认线程的个数为5
    private static int WORK_NUM = 5;
    
    // 队列默认任务个数为100
    private static int TASK_COUNT = 100;  
    
    // 工作线程组
    private WorkThread[] workThreads;

    // 任务队列，作为一个缓冲
    private final BlockingQueue<Runnable> taskQueue;
    
    // 用户在构造这个池，希望的启动的线程数
    private final int worker_num;

    // 创建具有默认线程个数的线程池
    public MyThreadPool() {
        this(WORK_NUM,TASK_COUNT);
    }

    // 创建线程池,worker_num为线程池中工作线程的个数
    public MyThreadPool(int worker_num, int taskCount) {
    	
    	if (worker_num <= 0) {
    		worker_num = WORK_NUM;
    	}
    	
    	if(taskCount <= 0) {
    		taskCount = TASK_COUNT;
    	}
    	
        this.worker_num = worker_num;
        // 阻塞队列
        taskQueue = new ArrayBlockingQueue<Runnable>(taskCount);
        // 工作线程
        workThreads = new WorkThread[worker_num];
        
        for(int i=0; i<worker_num; i++) {
        	workThreads[i] = new WorkThread();
        	workThreads[i].start();
        }
        
        //  获取机器的cpu核心数
        Runtime.getRuntime().availableProcessors();
    }

    // 执行任务,其实只是把任务加入任务队列，什么时候执行有线程池管理器决定
    public void execute(Runnable task) {
    	try {
    		// 向工作队列里加入新的任务线程
			taskQueue.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

    }

    // 销毁线程池,该方法保证在所有任务都完成的情况下才销毁所有线程，否则等待任务完成才销毁
    public void destroy() {
        // 工作线程停止工作，且置为null
        System.out.println(LogTool.time() + " ready close pool.....");
        
        // 任务全部中断
        for(int i=0; i<worker_num; i++) {
        	workThreads[i].stopWorker();
        	workThreads[i] = null;	//help gc
        }
        
        // 清空任务队列
        taskQueue.clear();
    }

    // 覆盖toString方法，返回线程池信息：工作线程个数和已完成任务个数
    @Override
    public String toString() {
        return LogTool.time() + "WorkThread number: " + worker_num
                + "  wait task number: " + taskQueue.size();
    }

    /**
     * 内部类，工作线程
     */
    private class WorkThread extends Thread{
    	
    	@Override
    	public void run(){
    		Runnable r = null;
    		try {
    			// 判断当前线程是否被终止
				while (!isInterrupted()) {
					// 从阻塞队列中获取线程
					r = taskQueue.take();
					if(r!=null) {
						System.out.println(LogTool.time() + getId() +" ready exec : " + r);
						r.run();
					}
					r = null; //help gc;
				} 
			} catch (Exception e) {
				// TODO: handle exception
			}
    	}
    	
    	// 终止任务
    	public void stopWorker() {
    		interrupt();
    	}
    	
    }
}
