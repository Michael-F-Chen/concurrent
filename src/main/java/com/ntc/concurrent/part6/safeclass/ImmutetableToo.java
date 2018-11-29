package com.ntc.concurrent.part6.safeclass;

import java.util.ArrayList;
import java.util.List;

/**
 * 不可变的类
 * 属性被暴露，但是不会改变属性的值
 * @author Michael-Chen
 */
public class ImmutetableToo {
	private List<Integer> list =  new ArrayList<>(3);
	
	public ImmutetableToo() {
		list.add(1);
		list.add(2);
		list.add(3);
	}
	
	public boolean isContains(int i) {
		return list.contains(i);
	}

}
