package com.ntc.concurrent.part2.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>演示Semaphore，一个数据库连接池的实现</h2>
 * @author Michael-Chen
 */
public class DBPoolSemaphore {
	// 数据库连接池的大小
	private final static int POOL_SIZE = 2;
	// useful表示可用的数据库连接，useless表示已用的数据库连接
	private final Semaphore useful, useless;
	
	// DB连接池的初始化
	public DBPoolSemaphore() {
		this.useful = new Semaphore(POOL_SIZE);
		this.useless = new Semaphore(0);
	}
	
	// 存放数据库连接的容器
	private static LinkedList<Connection> pool = new LinkedList<Connection>();
	
	// 初始化池
	static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
	}

	// 归还连接
	public void returnConnect(Connection connection) throws InterruptedException {
		if(connection != null) {
			System.out.println(LogTool.time() + "当前有【"+useful.getQueueLength()+"】个线程等待数据库连接！！"
					+ " 可用连接数: "+useful.availablePermits());
			
			// 申请已用连接的许可（已用连接是资源，内存中的空位也是资源，所以使用前，也需要申请）
			useless.acquire();
			
			// 连接归还
			synchronized (pool) {
				pool.addLast(connection);
			}	
			
			useful.release();
		}
	}
	
	// 从DBpool中获取连接，如果没有连接可以拿，就会被阻塞
	public Connection takeConnect() throws InterruptedException {
		useful.acquire();
		Connection conn;

		synchronized (pool) {
			// 拿pool中的第一个连接
			conn = pool.removeFirst();
		}
		
		useless.release();
		return conn;
	}
	
}
