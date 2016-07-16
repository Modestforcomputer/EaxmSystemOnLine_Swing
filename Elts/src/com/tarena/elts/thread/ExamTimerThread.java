package com.tarena.elts.thread;

import javax.swing.JLabel;

import com.tarena.elts.ui.ClientContext;

public class ExamTimerThread extends Thread{

	private JLabel timesLabel;//显示剩余时间的JLabel
	private int times ;//考试时间
	private long delayTime = 1000;//1秒的毫秒表示
	private int stopCountTimeFlag;//用户在考试时间内
						//点击交卷按钮导致的计时停止标志
	private int countAntoFinishFlag;//计时自动完成标志
						//（考试时间结束导致自动交卷标志）
	private ClientContext clientContext;
	
	public void setStopFlag(int stopFlag) {
		this.stopCountTimeFlag = stopFlag;
	}

	public void setExamTimerThread(JLabel timesLabel,int times) {
		this.timesLabel = timesLabel;
		this.times = times;
	}
	
	@Override
	public void run() {
		try {
			//倒计时
			while(times >= 0 && stopCountTimeFlag != 1){
				timesLabel.setText(times+"");
				sleep(delayTime);
				times--;
			}
			
			//当用户点击交卷按钮，则该标志为1，使得线程自动死亡
			if(stopCountTimeFlag == 1){
				return;
			}
			
			/**
			 *当考试时间结束（即系统自动交卷），设置该标志为1，
			 *并调用send()函数进行考试分数计算
			 *
			 **/
			countAntoFinishFlag = 1;
			clientContext.send(countAntoFinishFlag);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**对象注入属性*/
	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}
}
