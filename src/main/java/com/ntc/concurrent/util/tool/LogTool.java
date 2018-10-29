package com.ntc.concurrent.util.tool;

/**
 * 工具类
 * @author Michael-Chen
 */
public class LogTool {

	public static String tips(String value) {
		return "[ "+ value +" ] ";
	}
	

	public static final String time() {
    	return "[ Time = " + System.currentTimeMillis() + " ] ====> ";
    }
	
	public static final String newLine() {
    	return "\r\n                               ";
    }
}
