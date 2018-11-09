package com.ntc.concurrent.part4;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 *
 *使用显示锁的范式
 */
/**
 * <h2>演示Lock的使用</h2>
 * <ul>
 * <li>synchronized:代码简洁，获取锁不可被中断</li>
 * <li>Lock：获取锁可以被中断，超时获取锁，尝试获取锁，读多写少用读写锁（ReadWriteLock），其他情况使用synchronized</li>
 * <li>ReentrantLock：可重入锁（比如递归，需要重新获得锁）</li>
 * <li>公平锁的和非公平锁：先到先得锁是公平锁，可以被插队的锁是非公平锁（非公平锁效率较高，因为公平锁排队切换也需要时间，非公平锁可能释放锁正好有线程插队，无需切换）</li>
 * <li>ReentrantLock和Syn关键字，都是排他锁（同一时刻，只允许一个线程访问）</li>
 * <li>读写锁（ReentrantReadWriteLock实现ReadWriteLock接口）：同一时刻允许多个读线程同时访问，但是写线程访问的时候，所有的读和写都被阻塞，最适宜与读多写少的情况</li>
 * </ul>
 * @author Michael-Chen
 */
public class LockDemo {
	
	// 定义一个lock对象
	private Lock lock  = new ReentrantLock();
	private int count;
	
	// lock显式锁
	public void increament() {
		// 首先需要拿到锁
		lock.lock();
		
		// 为什么要try？因为拿到锁一定要释放，需要把lock.unlock()放到finally中，使用lock显示锁必须要遵从此范式
		try {
			count++;
		}finally {
			// 放到finally中是为了保证unlock一定会执行
			lock.unlock();
		}
	}
	
	// synchronized关键字锁
	public synchronized void incr2() {
		count++;
		incr2();
	}
	
	public synchronized void test3() {
		incr2();
	}

}
