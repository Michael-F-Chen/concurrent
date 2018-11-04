package com.ntc.concurrent.part2.forkjoin.sum;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import com.ntc.concurrent.util.tool.LogTool;
import com.ntc.concurrent.util.tool.SleepTools;

/**
 * <h2>使用ForkJoin</h2>
 * <ul>
 * <li>使用ForkJoin工具，将相同的问题，递归切分成若干份求解，再汇总。</li>
 * <li>注意点：阈值的使用，不是固定的，要根据具体情况进行分析</li>
 * <li>注意点：当样本值过小，使用多线程，效率可能比单线程慢（因为时间片理论转时，多线程上下文切换，会消耗一定的资源）</li>
 * </ul>
 * @author Michael-Chen
 */
public class SumMultiThread {
	
    private static class SumTask extends RecursiveTask<Integer>{

        private static final long serialVersionUID = -8831823105847709816L;
        // 阈值
		private final static int THRESHOLD = MakeArray.ARRAY_LENGTH / 10;
		// 表示我们要实际统计的数组(不要切分，切分设计到拷贝，会影响效率，直接使用下标)
		private int[] src; 
		// 开始统计的下标
        private int fromIndex;
        // 统计到哪里结束的下标
        private int toIndex;

        public SumTask(int[] src, int fromIndex, int toIndex) {
            this.src = src;
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }

		@Override
		protected Integer compute() {
			// 当计算范围小于阈值，直接进行计算
			if(toIndex - fromIndex < THRESHOLD) {
				int count = 0;
				for(int i=fromIndex;i<=toIndex;i++) {
			    	SleepTools.ms(1);
			    	count = count + src[i];
				}
				return count;
			}else {
				// 当计算范围大于等于阈值，再次从中间拆分，用递归处理拆分的任务
				// fromIndex....mid....toIndex
				// 1............70.....100
				int mid = (fromIndex+toIndex)/2;
				SumTask left = new SumTask(src, fromIndex, mid);
				SumTask right = new SumTask(src, mid+1, toIndex);
				// 将拆分的任务交给外面的线程池，进行执行
				invokeAll(left,right);
				// 拿到拆分后任务的值
				return left.join() + right.join();
			}
		}
    }

    /**
     * @param args
     * @return
     * <pre>
     *[ Time = 1541257106906 ] ====> SumMultiThread start.
[ Time = 1541257107913 ] ====> SumMultiThread end.
The count is 24186586 spend time:1007ms
     * </pre>
     */
    public static void main(String[] args) {

    	// 使用ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();
        // 获取数据
        int[] src = MakeArray.makeArray();
        // 定义任务
        SumTask innerFind = new SumTask(src, 0, src.length-1);
        // 开始时刻
        long start = System.currentTimeMillis();
        
        System.out.println(LogTool.time() + "SumMultiThread start.");
        // 执行任务
        pool.invoke(innerFind);
        System.out.println(LogTool.time() + "SumMultiThread end.");
        System.out.println("The count is "+innerFind.join()
                +" spend time:"+(System.currentTimeMillis()-start)+"ms");

    }
}
