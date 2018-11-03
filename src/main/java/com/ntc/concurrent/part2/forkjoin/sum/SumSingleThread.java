package com.ntc.concurrent.part2.forkjoin.sum;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;

/**
 * 常规数组求和（单线程）
 * @author Michael-Chen
 */
public class SumSingleThread {
	
	/**
	 * @param args
	 * @return
	 * <pre>
	 *[ Time = 1541256206791 ] ====> SumSingleThread start.
[ Time = 1541256210798 ] ====> SumSingleThread end.
The count is 23875016 spend time:4008ms
	 * </pre>
	 * @see SumMultiThread
	 */
	public static void main(String[] args) {
	    int count = 0;
	    // 获取数组
	    int[] src = MakeArray.makeArray();
	    // 开始时刻
	    long start = System.currentTimeMillis();
	   
	    System.out.println(LogTool.time() + "SumSingleThread start.");
	    for(int i = 0; i < src.length; i++){
	    	// 每加一次，睡1毫秒
	    	SleepTools.ms(1);
	    	// 求和
	    	count = count + src[i];
	    }
	    System.out.println(LogTool.time() + "SumSingleThread end."); 
		System.out.println( "The count is "+ count
	            +" spend time:"+(System.currentTimeMillis()-start)+"ms");
	}

}
