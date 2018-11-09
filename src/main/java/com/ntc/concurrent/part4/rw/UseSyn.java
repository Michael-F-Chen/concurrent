package com.ntc.concurrent.part4.rw;

import com.ntc.concurrent.util.tool.SleepTools;

/**
 * 用内置锁来实现商品服务接口
 * @author Michael-Chen
 */
public class UseSyn implements GoodsService {
	
	private GoodsInfo goodsInfo;
	
	public UseSyn(GoodsInfo goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	// 使用synchronized关键字修饰获取方法
	public synchronized GoodsInfo getNum() {
		SleepTools.ms(5);
		return this.goodsInfo;
	}

	// 使用synchronized关键字修饰写入方法
	public synchronized void setNum(int number) {
		SleepTools.ms(5);
		goodsInfo.changeNumber(number);

	}

}
