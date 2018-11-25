package com.ntc.concurrent.part6.schd;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 演示ScheduledThreadPoolExecutor的用法
 * @author Michael-Chen
 */
public class ScheduledCase {
    public static void main(String[] args) {
    	
    	ScheduledThreadPoolExecutor schedule 
    		= new ScheduledThreadPoolExecutor(1);
    	
    	schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.HasException), 
    			1000, 3000, TimeUnit.MILLISECONDS);
    	schedule.scheduleAtFixedRate(new ScheduleWorker(ScheduleWorker.Normal), 
    			1000, 3000, TimeUnit.MILLISECONDS);    	
    	
    }
}
