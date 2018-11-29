package com.ntc.concurrent.part6.safeclass;


/**
 * 看起来不可变的类，实际是可变的
 * @author Michael-Chen
 */
public class ImmutableFinalRef {
	
	private final int a;
	private final int b;
	private final User user;	// 这里，就不能保证线程安全
	
	public ImmutableFinalRef(int a, int b) {
		super();
		this.a = a;
		this.b = b;
		this.user = new User(1);
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}
	
	public User getUser() {
		return user;
	}

	public static class User{
		private int age;

		public User(int age) {
			super();
			this.age = age;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
		
	}
	
	public static void main(String[] args) {
		ImmutableFinalRef ref = new ImmutableFinalRef(12,23);
		User u = ref.getUser();
		//u.setAge(35);
	}
}
