package com.ntc.concurrent.part4.rw;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.ntc.concurrent.util.tool.SleepTools;


/**
 * 演示使用读写锁
 * @author Michael-Chen
 */
public class UseRwLock implements GoodsService {

	// 商品类
    private GoodsInfo goodsInfo;
    // 定义读写锁（缺省值代表非公平锁）
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    // 读锁
    private final Lock getLock = lock.readLock();
    // 写锁
    private final Lock setLock = lock.writeLock();

    public UseRwLock(GoodsInfo goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    // 获取商品信息
	public GoodsInfo getNum() {
		// 获取读的显式锁
		getLock.lock();
		try {
			SleepTools.ms(5);
			return this.goodsInfo;
		}finally {
			// 释放锁
			getLock.unlock();
		}
		
	}

	// 设置商品信息
	public void setNum(int number) {
		// 获取写的显式锁
		setLock.lock();
		try {
			SleepTools.ms(5);
			goodsInfo.changeNumber(number);
		}finally {
			// 释放锁
			setLock.unlock();
		}
	}
}
