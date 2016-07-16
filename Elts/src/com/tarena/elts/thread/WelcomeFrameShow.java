package com.tarena.elts.thread;

import com.tarena.elts.ui.WelcomeFrame;
/**
 * 创建一个新线程
 * 显示欢迎界面
 * 一段时间后，界面消失
 * 
 * */
public class WelcomeFrameShow extends Thread{

	private WelcomeFrame welcomeFrame = new WelcomeFrame();
	private long showTimes = 2000 ;//显示时间长度
	
	@Override
	public void run() {
		welcomeFrame.setVisible(true);
		try {
			sleep(showTimes);
			welcomeFrame.setVisible(false);
		} catch (InterruptedException e) {
			System.err.println("欢迎界面显示线程出错！");
		}
	}

}
