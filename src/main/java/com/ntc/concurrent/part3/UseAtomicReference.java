package com.ntc.concurrent.part3;

import java.util.concurrent.atomic.AtomicReference;

import com.ntc.concurrent.util.tool.LogTool;

/**
 * <h2>演示引用类型的原子操作类</h2>
 * <ul>
 * <li>原子类的修改，并不会修改原始引用的实体类，只会修改原子类的引用对象</li>
 * </ul>
 * @author Michael-Chen
 */
public class UseAtomicReference {
	
	// 用AtomicReference将需要引用的类包装起来
	static AtomicReference<UserInfo> userRef = new AtomicReference<UserInfo>();
	
	/**
	 * @param args
	 * @return
	 * <pre>
	 *[ 原子类 ] -> Michael
[ 新的实体类 ] -> Bill
[ 修改后的原子类 ] -> Bill
[ 原始的实体类 ] -> Michael
	 * </pre>
	 */
    public static void main(String[] args) {
    	// 实例化引用的类
        UserInfo user = new UserInfo("Michael", 15);
        
        // 将实例设置到AtomicReference的实例中
        userRef.set(user);
        
        System.out.println(LogTool.tips("原子类") + "-> " + userRef.get().getName());
        
        // 要变化的新实例
        UserInfo updateUser = new UserInfo("Bill", 17);
        System.out.println(LogTool.tips("新的实体类") + "-> " + updateUser.getName());
        
        // AtomicReference先比较，再设置，第一个参数为期望的修改对象，第二个参数为修改后的对象
        // 此修改，并非修改原始的实体类，而是修改原子类里的引用
        userRef.compareAndSet(user, updateUser);
        
        System.out.println(LogTool.tips("修改后的原子类") + "-> " + userRef.get().getName());
        
        System.out.println(LogTool.tips("原始的实体类") + "-> " + user.getName());
    }
    
    // 定义一个实体类
    static class UserInfo {
        private String name;
        private int age;
        public UserInfo(String name, int age) {
            this.name = name;
            this.age = age;
        }
        public String getName() {
            return name;
        }
        public int getAge() {
            return age;
        }
    }

}
