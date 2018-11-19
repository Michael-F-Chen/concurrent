package com.ntc.concurrent.part5.bq;

import java.util.concurrent.DelayQueue;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * 测试延时订单
 * @author Michael-Chen
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
    	
    	DelayQueue<ItemVo<Order>> queue = new DelayQueue<ItemVo<Order>>();
    	new Thread(new PutOrder(queue)).start();
    	new Thread(new FetchOrder(queue)).start();

        //每隔500毫秒，打印个数字
        for(int i=1;i<15;i++){
            Thread.sleep(500);
            System.out.println(LogTool.time() + i*500);
        }
    }
}
