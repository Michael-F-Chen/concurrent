package com.ntc.concurrent.part1;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import com.ntc.concurrent.util.Tool;

/**
 * 测试只运行main方法时，产生的线程数
 * 
 * @author Michael-Chen
 */
public class OnlyMain {

	/**
	 * 只运行Main方法时，会有产生多个线程，证明java本身就是多线程的。
	 * @return
	 * 		<p> [ 8 ] JDWP Command Reader </p>
	 * 		<p> [ 7 ] JDWP Event Helper Thread </p>
     * 		<p> [ 6 ] JDWP Transport Listener: dt_socket </p>
     * 		<p> [ 5 ] Attach Listener </p>
     * 		<p> [ 4 ] Signal Dispatcher </p>
     * 		<p> [ 3 ] Finapzer </p>
     * 		<p> [ 2 ] Reference Handler </p>
     * 		<p> [ 1 ] main </li>
	 */
	public static void main(String[] args) {
		// 虚拟机线程管理的接口
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		for (ThreadInfo threadInfo : dumpAllThreads) {
			System.out.println(Tool.parentheses(threadInfo.getThreadId()+"") + " " +threadInfo.getThreadName());
		}
		
	}
}
