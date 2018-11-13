package com.ntc.concurrent.part4.template;

/**
 * <h2>演示模板方法的调用</h2>
 * @author Michael-Chen
 */
public class SendSms extends SendCustom {

	@Override
	public void to() {
		System.out.println("Mark");
	}

	@Override
	public void from() {
		System.out.println("Michael");
	}

	@Override
	public void content() {
		System.out.println("Hello");
	}

	@Override
	public void send() {
		System.out.println("Send sms");
	}
	
	public static void main(String[] args) {
		SendCustom sendCustom = new SendSms();
		sendCustom.sendMessage();
	}

}
