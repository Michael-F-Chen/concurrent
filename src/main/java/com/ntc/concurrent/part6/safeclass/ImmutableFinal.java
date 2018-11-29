package com.ntc.concurrent.part6.safeclass;


/**
 * 不可变的类
 * 属性暴露出去，但是属性不可变
 * @author Michael-Chen
 */
public class ImmutableFinal {
	
	private final int a;
	private final int b;
	
	public ImmutableFinal(int a, int b) {
		super();
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}
	

}
