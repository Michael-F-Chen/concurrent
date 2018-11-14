package com.ntc.concurrent.part4.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义的基于AQS的锁
 * @author Michael-Chen
 */
public class SelfLock implements Lock {
	
	// 内部类，继承自AQS
	// state == 1 表示获取到锁
	// state == 0 表示当前没有线程拿到这个锁
	private static class Sync extends AbstractQueuedSynchronizer {
		
		private static final long serialVersionUID = -7122233147197896190L;

		// 判断当前线程是否是独占式的，直接返回  是否获取到锁
		@Override
		protected boolean isHeldExclusively() {
			 return getState() == 1;
		}
		
		// 试着去获取
		@Override
		protected boolean tryAcquire(int arg) {
			// 使用CAS 设置当前state为获取到锁的状态，即getState() == 1
			// 此锁为不可重入锁，当第一个线程拿到锁，state为1，当第二个线程再试着获取锁，state不会为0，造成死锁
			if (compareAndSetState(0, 1)) {
				// 如果设置成功，就将当前线程设置成独占线程
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		
		// 尝试去释放锁
		@Override
		protected boolean tryRelease(int arg) {
			// 当状态为0，即没有线程获取到锁，即不需释放，此释放失败
			if (getState() == 0) {
				throw new UnsupportedOperationException();
			}
			// 将null设置为独占线程（即没有线程是独占线程）
			setExclusiveOwnerThread(null);
			
			// 重点！！！ 为什么这里不用CAS设置？
			// 因为当前为独占模式，所以不会有其他线程来竞争
			setState(0);
			return true;
		}
		
		Condition newCondition(){
			return new ConditionObject();
		} 
	}
	
	private final Sync sync = new Sync();
	
	// 覆盖Lock的方法，sync尝试获取锁
	public void lock() {
		// 为什么参数是1？因为这是个独占锁
		sync.acquire(1);
	}

	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	public void unlock() {
		sync.release(1);
	}

	public Condition newCondition() {
		return sync.newCondition();
	}
}
