package com.ntc.concurrent.part2.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Exchanger;

/**
 * <h2>Exchange的使用</h2>
 * <ul>
 * <li>让一组线程达到某个屏障，被阻塞，直到组内最后一个线程达到屏障时，屏障开放，所有被阻塞的线程会继续运行（和CountDownLatch的主要区别）</li>
 * <li>CyclicBarrier放行由一组线程本身控制，放行条件=线程数</li>
 * <li>应用场景：多线程处理excel，当所有线程处理完毕后，再进行数据汇总</li>
 * </ul>
 * @author Michael-Chen
 */
public class UseExchange {
    private static final Exchanger<Set<String>> exchange 
    	= new Exchanger<Set<String>>();

    public static void main(String[] args) {

    	//第一个线程
        new Thread(new Runnable() {
            public void run() {
            	Set<String> setA = new HashSet<String>();//存放数据的容器
                try {
                	/*添加数据
                	 * set.add(.....)
                	 * */
                	setA = exchange.exchange(setA);//交换set
                	/*处理交换后的数据*/
                } catch (InterruptedException e) {
                }
            }
        }).start();

      //第二个线程
        new Thread(new Runnable() {
            public void run() {
            	Set<String> setB = new HashSet<String>();//存放数据的容器
                try {
                	/*添加数据
                	 * set.add(.....)
                	 * set.add(.....)
                	 * */
                	setB = exchange.exchange(setB);//交换set
                	/*处理交换后的数据*/
                } catch (InterruptedException e) {
                }
            }
        }).start();

    }
}
