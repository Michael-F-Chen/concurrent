package com.ntc.concurrent.part3;

import java.util.concurrent.atomic.AtomicIntegerArray;


/**
 * <h2>演示原子数组类</h2>
 * <ul>
 * <li>对原子数组类的元素进行赋值，不会改变原始被包装的数组（不会更改引用）</li>
 * </ul>
 * @author Michael-Chen
 */
public class AtomicArray {
    static int[] value = new int[] { 1, 2 };
    
    static AtomicIntegerArray ai = new AtomicIntegerArray(value);
    
    /**
     * @param args
     * @return
     * <pre>
     *原数组第一位元素为： 1
将AtomicIntegerArray实例第一位赋值为：3
AtomicIntegerArray实例第一位为：3
原数组第一位元素为： 1

     * </pre>
     */
    public static void main(String[] args) {
    	System.out.println("原数组第一位元素为： " + value[0]);
    	
    	System.out.println("将AtomicIntegerArray实例第一位赋值为：3");
    	ai.getAndSet(0, 3);

    	System.out.println("AtomicIntegerArray实例第一位为：" + ai.get(0));
    	
    	System.out.println("原数组第一位元素为： " + value[0]);

    }
}
