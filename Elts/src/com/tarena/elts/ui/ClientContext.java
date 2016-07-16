package com.tarena.elts.ui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;
import com.tarena.elts.service.ExamService;
import com.tarena.elts.service.IdOrPwdException;
import com.tarena.elts.thread.ExamTimerThread;
import com.tarena.elts.thread.WelcomeFrameShow;

/**界面控制器*/
public class ClientContext {
	
	/**登录界面对象的引用 达到控制器能够操作界面*/
	private LoginFrame loginFrame;
	private MenuFrame menuFrame;
	/**添加考试界面的属性*/
	private ExamFrame examFrame;
	private ExamService examService;
	/**当前题属性*/
	private int currentQuestionIndex;
	/**考试信息*/
	private ExamInfo examInfo = new ExamInfo();
	/**剩余时间（计时器）*/
	private ExamTimerThread examTimeThread = new ExamTimerThread();
	
	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}
	
	/**菜单界面对象的引用 达到控制器能够操作界面*/
	public void setMenuFrame(MenuFrame menuFrame) {
		this.menuFrame = menuFrame;
	}
	
	/**添加考试界面的set方法*/
	public void setExamFrame(ExamFrame examFrame) {
		this.examFrame = examFrame;
	}
	
	/**增加业务模型实现的属性  达到控制器能够调用业务模型中的方法*/
	public void setExamService(ExamService examService) {
		this.examService = examService;
	}
	
	/**
	 * 登录
	 * 1.从登录界面获取用户ID和PWD
	 * 2.调用业务模型的login方法 完成用户登录
	 * 3.根据结果更新界面  显示提示信息：
	 * 				关闭登录界面，打开菜单界面（登录成功）
	 * 4.登录失败  显示失败原因
	 * 
	 * */
	//被界面的登录按钮调用
	public void login(){
		try{
			String id = loginFrame.getUserId();
			String pwd = loginFrame.getPassword();
			User user = examService.login(Integer.parseInt(id), pwd);
			
			//更新菜单界面
			menuFrame.updateView(user.getName());
			loginFrame.setVisible(false);//登录成功 关闭登录界面
			menuFrame.setVisible(true);//显示菜单界面
		}catch(IdOrPwdException e){
			/**执行用户ID或PWD错误的逻辑*/
			loginFrame.showMessage("登录失败"+e.getMessage());
		}catch(NumberFormatException e){
			loginFrame.showMessage("ID必须是整数");
		}catch(Exception e){
			loginFrame.showMessage("登录失败"+e.getMessage());
		}
	}
	
	/**
	 * 定义开始考试的方法start()  
	 * 调用ExamService的start()  返回一个ExamInfo对象  包含考试信息
	 * 调用ExamService的getQuestionInfo(0)  获得一道考题 用更新考试界面
	 * 更新考试界面  调用考试界面的更新方法updateView（ExamInfo examInfo
	 * 									,QuestionInfo questionInfo）
	 * 关闭菜单界面
	 * 显示考试界面
	 * 
	 * */
	public void start() {
		
		try {
			examInfo = examService.start();
			QuestionInfo questionInfo = examService.getQuestionInfo(0);
			examFrame.updateView(examInfo,questionInfo);
			menuFrame.setVisible(false);
			examFrame.setVisible(true);
			examTimeThread.setExamTimerThread(examFrame.getTimers(),examInfo.getTimeLimit());
			examTimeThread.setClientContext(this);
			examTimeThread.start();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());
		}
		
	}
	
	/**上一题*/
	public void last() {
		List<Integer> userAnswers = examFrame.getUserAnswers();
		examService.getQuestionInfo(currentQuestionIndex).setUserAnswers(userAnswers);
		if(currentQuestionIndex == 0){
			JOptionPane.showConfirmDialog(examFrame, "已经是第一题","提示",JOptionPane.CLOSED_OPTION);
			return;
		}
		currentQuestionIndex--;
		QuestionInfo questionInfo = examService.getQuestionInfo(currentQuestionIndex);
		examFrame.updateView(questionInfo);
	}
	
	/**添加next()
	 * 得到questionInfo   必须是下一个考题
	 * 调用ExamFrame的updateView方法
	 * 
	 * */
	public void next(){
		List<Integer> userAnswers = examFrame.getUserAnswers();
		examService.getQuestionInfo(currentQuestionIndex).setUserAnswers(userAnswers);
		if(currentQuestionIndex == examService.getExamInfo().getQuestionCount()-1){
			JOptionPane.showConfirmDialog(examFrame, "已经是最后一题","提示",JOptionPane.CLOSED_OPTION);
			return;
		}
		currentQuestionIndex++;
		QuestionInfo questionInfo = examService.getQuestionInfo(currentQuestionIndex);
		examFrame.updateView(questionInfo);
	}
	
	/**
	 * 交卷
	 * 
	 * */
	public void send(int countFinishFlag) {
		List<Integer> userAnswers = examFrame.getUserAnswers();
		examService.getQuestionInfo(currentQuestionIndex).setUserAnswers(userAnswers);
		String message = "分数：";
		if(countFinishFlag != 1){
			int result = JOptionPane.showConfirmDialog(examFrame,"确定提交吗？","提示",JOptionPane.YES_NO_OPTION);
			if(result != JOptionPane.YES_OPTION){
				return;
			}
			examTimeThread.setStopFlag(1);
			int score = examService.over();
			JOptionPane.showMessageDialog(examFrame, message+score);
		}else{
			int score = examService.over();
			JOptionPane.showMessageDialog(examFrame, "考试时间到 !         " + message+score);
		}
		examFrame.setVisible(false);
		menuFrame.setVisible(true);
	}
	
	/**界面关闭方法*/
	public void exit(JFrame jframe){
		int result = JOptionPane.showConfirmDialog(jframe, "确定退出考试系统？","提示",JOptionPane.YES_NO_OPTION);
		if(result == JOptionPane.YES_OPTION){
			System.exit(0);//退出
		}
	}
	
	/**界面显示方法*/
	public void show(){
		
		/**
		 *显示欢迎界面 
		 * 
		 **/
		WelcomeFrameShow welcomeFrameShow = new WelcomeFrameShow();
		welcomeFrameShow.start();
		try {
			welcomeFrameShow.join();
		} catch (InterruptedException e) {
			System.err.println("显示欢迎界面线程的join方法出错");
		}
		
		/**
		 * 显示登录界面
		 * 
		 * */
		loginFrame.setVisible(true);
	}

	/**添加getScore
	 *获得分数
	 *显示分数
	 *
	 * */
	public void getScore() {
		
		try {
			int score = examService.getScore();
			JOptionPane.showConfirmDialog(menuFrame,"分数"+score,"查询分数",JOptionPane.CLOSED_OPTION);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());
		}
	}

	/**获得考试规则*/
	public void getExamRules() {
		String rules = examService.getExamRules();
		JOptionPane.showMessageDialog(menuFrame, rules, "考试规则", JOptionPane.CLOSED_OPTION);
		
	}
}
