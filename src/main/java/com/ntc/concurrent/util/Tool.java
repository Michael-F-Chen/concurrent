package com.ntc.concurrent.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 * @author Michael-Chen
 */
public class Tool {

	public static String parentheses(String value) {
		return "[ "+ value +" ] ";
	}
	
	public static String nowTime(String value) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");//设置日期格式
		return "[ "+ df.format(new Date())+" ] ====> " + value;
	}
}
