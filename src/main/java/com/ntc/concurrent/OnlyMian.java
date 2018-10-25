package com.ntc.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Iterator;

import com.ntc.concurrent.util.concurrentUtil;

/**
 * 测试只有main方法时，的线程数
 * 
 * @author Michael-Chen
 */
public class OnlyMian {

	public static void main(String[] args) {
		// 虚拟机线程管理的接口
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		for (ThreadInfo threadInfo : dumpAllThreads) {
			System.out.println(concurrentUtil.parentheses(threadInfo.getThreadId()+""));
		}
		
	}
}
