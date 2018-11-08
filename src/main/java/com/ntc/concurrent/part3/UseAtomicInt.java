package com.ntc.concurrent.part3;

import java.util.concurrent.atomic.AtomicInteger;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>演示基本类型类原子操作</h2>
 * @author Michael-Chen
 */
public class UseAtomicInt {
	
	static AtomicInteger ai = new AtomicInteger(10);
	
	/**
	 * @param args
	 * @return
	 * <pre>
	 *[ get ] -> 10
[ getAndIncrement ] -> 10
[ incrementAndGet ] -> 12
[ get ] -> 12
	 * </pre>
	 */
    public static void main(String[] args) {
    	
    	System.out.println(LogTool.tips("get") + "-> " + ai.get());
    	
    	// getAndIncrement（先返回获取的值，再累加） 
    	System.out.println(LogTool.tips("getAndIncrement") + "-> " + ai.getAndIncrement());
    	
    	// incrementAndGet（先累加，再返回获取的值） 
    	System.out.println(LogTool.tips("incrementAndGet") + "-> " + ai.incrementAndGet());
    	
    	System.out.println(LogTool.tips("get") + "-> " + ai.get());
    }
}
