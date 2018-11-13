package com.ntc.concurrent.part4.template;

import java.util.Date;

/**
 * <h2>演示模板方法的使用(就是父类将需要覆盖的方法写好，让子类去覆盖)</h2>
 * 发送消息给客户的类
 * @author Michael-Chen
 */
public abstract class SendCustom {

	public abstract void to();
	public abstract void from();
	public abstract void content();
	public void date(){
		System.out.println(new Date());
	};
	public abstract void send();
	
	// 父类依次调用，统一发送信息
	public void sendMessage() {
		to();
		from();
		content();
		date();
		send();
	}
}
