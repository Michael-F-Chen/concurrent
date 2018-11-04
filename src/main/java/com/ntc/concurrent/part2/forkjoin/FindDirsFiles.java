package com.ntc.concurrent.part2.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>使用ForkJoin（异步方法，演示无返回结果值）</h2>
 * 遍历指定目录（含子目录）找寻指定类型文件
 * @author Michael-Chen
 */
public class FindDirsFiles extends RecursiveAction {
	// RecursiveAction 有返回值
	
	private static final long serialVersionUID = -1596856432190288419L;
	private File path; // 当前任务需要搜寻的目录

	public FindDirsFiles(File path) {
		this.path = path;
	}
	
	@Override
	protected void compute() {
		// 定义子任务
		List<FindDirsFiles> subTasks = new ArrayList<FindDirsFiles>();
		
		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				// 如果文件是文件夹
				if (file.isDirectory()) {
					// 以此文件为参数，定义新的子任务
					subTasks.add(new FindDirsFiles(file));
				} else {
					//遇到文件，检查
					if (file.getAbsolutePath().endsWith("mp3")) {
						System.out.println(LogTool.time() + "文件： " + file.getAbsolutePath());
					}
				}
			}
			// 当子任务集合不为空
			if (!subTasks.isEmpty()) {
				for (FindDirsFiles subTask : invokeAll(subTasks)) {
					// 将子任务，join()到当前线程
					subTask.join();//等待子任务执行完成
				}
			}
		}
	}
	
	/**
	 * 寻找目标目录下的特定类型文件（异步），主线程进行其他任务
	 * @param args
	 * @return
	 *[ Time = 1541317411892 ] ====> Task is Running......
[ Time = 1541317411895 ] ====> Main Thread done sth......, otherWork=4950
[ Time = 1541317412164 ] ====> 文件： D:\03临时下载区\05-08 (1).mp3
[ Time = 1541317412164 ] ====> 文件： D:\03临时下载区\05-08.mp3
[ Time = 1541317547893 ] ====> Task end.
	 */
	public static void main(String[] args) {
		try {
			ForkJoinPool pool = new  ForkJoinPool();
			FindDirsFiles task = new FindDirsFiles(new File("D:/"));
			
			// 异步调用
			System.out.println(LogTool.time() + "Task is Running......" );
			pool.execute(task);
			
			Thread.sleep(1);
			// 主线程进行其他任务
			int otherWork = 0;
			for (int i = 0; i < 100; i++) {
				otherWork = otherWork + i;
			}
			
			System.out.println(LogTool.time() + "Main Thread done sth......, otherWork=" + otherWork);
			task.join();	// join()是个阻塞方法
			System.out.println(LogTool.time() + "Task end.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
