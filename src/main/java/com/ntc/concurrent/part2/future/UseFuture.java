package com.ntc.concurrent.part2.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;


/**
 * 
 * <h2>演示Future等的使用</h2>
 * <ul>
 * <li>Callable需要包装成FutureTask交给Thread去启动</li>
 * <li>
 * 		应用场景：对于某种计算任务，比较费时，并且将来需要使用此计算结果。
 * 		<br>
 * 		如：处理文档，文档中含有文字和图片（位于云上），可以判断遇到有图片就启用新线程去获取图片，主线程继续处理文档
 * </li>
 * </ul>
 * @author Michael-Chen
 */
public class UseFuture {
	
	// 实现Callable接口，允许有返回值
	private static class UseCallable implements Callable<Integer>{
		private int sum;
		
		public Integer call() throws Exception {
			System.out.println(LogTool.time() + "Callable子线程开始计算");
			for(int i=0; i<5000; i++) {
				sum = sum+i;
				SleepTools.ms(1);
			}
			System.out.println(LogTool.time() + "Callable子线程计算完成，结果 = " + sum);
			return sum;
		}

	}
	
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @return
	 * <pre>
	 *[ Time = 1541599401067 ] ====> Callable子线程 启动
[ Time = 1541599401067 ] ====> Callable子线程开始计算
[ Time = 1541599402068 ] ====> 中断计算
[ Time = 1541599402068 ] ====> 任务是否中断成功： true
[ Time = 1541599402068 ] ====> 任务是否在完成前被取消： true
[ Time = 1541599402068 ] ====> 任务是否结束： true
[ Time = 1541599406073 ] ====> Callable子线程计算完成，结果 = 12497500

[ Time = 1541599435544 ] ====> Callable子线程 启动
[ Time = 1541599435544 ] ====> Callable子线程开始计算
[ Time = 1541599440552 ] ====> Callable子线程计算完成，结果 = 12497500
[ Time = 1541599436545 ] ====> Get UseCallable result = 12497500
[ Time = 1541599440552 ] ====> 任务是否在完成前被取消： false
[ Time = 1541599440552 ] ====> 任务是否结束： true
	 * </pre>
	 */
	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		
		UseCallable useCallable = new UseCallable();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(useCallable);
		
		System.out.println(LogTool.time() + "Callable子线程 启动");
		new Thread(futureTask).start();
		
		Random r = new Random();
		SleepTools.second(1);
		
		// 随机决定是获得结果还是终止任务
		if(r.nextBoolean()) {
			System.out.println(LogTool.time() + "Get UseCallable result = " + futureTask.get());
		}else {
			System.out.println(LogTool.time() + "中断计算");
			
			// 将任线程中断，但是是否中断，有线程自己决定
			// 1、任务还没开始，返回false
			// 2、任务已经启动，cancel（true），中断正在运行的任务，中断成功，返回true，cancel（false），不会去中断已经运行的任务
			// 3、任务已经结束，返回false
			System.out.println(LogTool.time() + "任务是否中断成功： " + futureTask.cancel(true));
		}
		
		// isCancelled():任务完成前被取消，返回true
		System.out.println(LogTool.time() + "任务是否在完成前被取消： " + futureTask.isCancelled());
		
		// isDone():结束，正常还是异常结束，或者自己取消，返回true；
		System.out.println(LogTool.time() + "任务是否结束： " +futureTask.isDone());
		
	}

}
