package com.ntc.concurrent.part1.thread;

/**
 * @author Michael-Chen
 */
public class UseJion {

	static class JumpQueue implements Runnable {
		private Thread thread;
		
		public JumpQueue (Thread thread) {
			this.thread = thread;
		}
		
		public void run() {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread previous = Thread.currentThread();
		
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new JumpQueue(previous), String.valueOf(i));
			System.out.println(previous.getName() + "jump a queue the thread: " + thread.getName());
			thread.start();
			previous = thread;
		}
		
		Thread.sleep(2000);
		
	}
}
