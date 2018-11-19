package com.ntc.concurrent.part5.bq;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 存放到队列的元素
 * @author Michael-Chen
 */
public class ItemVo<T> implements Delayed{
	
	// 到期时间，单位毫秒
	private long activeTime;
	private T date;
	
	// activeTime是个过期时长
	public ItemVo(long activeTime, T date) {
		super();
		// 将传入的时长转换为超时的时刻（从毫秒转成纳秒）
		this.activeTime = TimeUnit.NANOSECONDS.convert(activeTime, 
				TimeUnit.MILLISECONDS) + System.nanoTime();
		this.date = date;
	}
	
	public long getActiveTime() {
		return activeTime;
	}

	public T getDate() {
		return date;
	}

	//按照剩余时间排序
	public int compareTo(Delayed o) {
		long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
		return (d==0)?0:((d>0)?1:-1);
	}

	//返回元素的剩余时间
	public long getDelay(TimeUnit unit) {
		long d = unit.convert(this.activeTime - System.nanoTime(),
				TimeUnit.NANOSECONDS);
		return d;
	}
	



}
