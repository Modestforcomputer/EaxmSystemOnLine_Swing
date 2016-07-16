package com.tarena.elts.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.util.List;
import java.util.ArrayList;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;

/**
 * 考试面板
 * 
 * */
@SuppressWarnings("serial")
public class ExamFrame extends JFrame{
	
	/**界面中的部件*/
	private JLabel examInfo = new JLabel("",JLabel.CENTER);//考试信息
	private JTextArea questionArea = new JTextArea();//题目区
	private JLabel questionCount = new JLabel("");//计题器
	private JPanel selectionPane = new JPanel();
	private Option answerA = new Option(0,"A");
	private Option answerB = new Option(1,"B");
	private Option answerC = new Option(2,"C");
	private Option answerD = new Option(3,"D");
	private List<Option> options = new ArrayList<Option>();
	JLabel timers = new JLabel("-----");//剩余时间显示区域
	
	public JLabel getTimers() {
		return timers;
	}

	/**控制器*/
	private ClientContext clientContext;
	
	public ExamFrame(){
		init();
	}
	
	/**初始化界面*/
	private void init() {
		this.setTitle("XXX在线考试");
		this.setSize(800,600);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(createContentPane());
	}
	
	/**主面板逻辑*/
	private Container createContentPane() {
		JPanel examMainPane = new JPanel(new BorderLayout());
		JLabel titlePane = new JLabel(new ImageIcon("resource/exam_title.png"));
		examMainPane.add(titlePane,BorderLayout.NORTH);
		examMainPane.add(createCenterPane(),BorderLayout.CENTER);//中间
		examMainPane.add(createToolsPane(),BorderLayout.SOUTH);//南部
		return examMainPane;
	}
	
	/**主面板的中部*/
	private Component createCenterPane() {
		JPanel examContentPane  = new JPanel(new BorderLayout());
		examContentPane.add(examInfo,BorderLayout.NORTH);//考试信息
		examContentPane.add(createQuestionPane(),BorderLayout.CENTER);//考试题目
		examContentPane.add(createSelectionPane(),BorderLayout.SOUTH);//选择答案区域
		return examContentPane;
	}
	
	/**主面板中部的考试题目区域*/
	private Component createQuestionPane() {
		JScrollPane questionPane = new JScrollPane();
		questionPane.setBorder(new TitledBorder("题目:"));
		questionArea.setLineWrap(true);//考题自动换行
		questionArea.setEditable(false);//不允许修改题目区域的内容
		/**获取可视区域*/
		questionPane.getViewport().add(questionArea);
		return questionPane;
	}
	
	/**主面板中部的考生选择答案区域*/
	private Component createSelectionPane() {
		selectionPane.add(answerA);
		selectionPane.add(answerB);
		selectionPane.add(answerC);
		selectionPane.add(answerD);
		options.add(answerA);
		options.add(answerB);
		options.add(answerC);
		options.add(answerD);
		return selectionPane;
	}
	
	/**主面板的南部工具面板*/
	private Component createToolsPane() {
		JPanel toolsPane = new JPanel(new BorderLayout());
		toolsPane.setBorder(new EmptyBorder(0,10,0,10));
		JPanel temp = new JPanel();
		temp.add(questionCount);
		toolsPane.add(temp,BorderLayout.WEST);
		toolsPane.add(createBtnPane(),BorderLayout.CENTER);//按钮区
		JPanel timePane = new JPanel();
		JLabel timer = new JLabel("剩余时间:	");
		JLabel timerUnit = new JLabel(" 秒");
		timePane.add(timer);
		timePane.add(timers);
		timePane.add(timerUnit);
		toolsPane.add(timePane,BorderLayout.EAST);
		return toolsPane;
	}
	
	private Component createBtnPane() {
		JPanel buttonsPane = new JPanel();
		JButton last = new JButton("上一题");
		JButton next = new JButton("下一题");
		JButton send = new JButton("交卷");
		/**给下一题按钮添加事件 触发调用控制器的next()*/
		last.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.last();
			}
		});
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.next();
			}
		});
		
		/**给交卷按钮添加事件*/
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.send(0);
			}
		});
		last.setFocusable(false);
		next.setFocusable(false);
		send.setFocusable(false);
		buttonsPane.add(new JLabel("                 "));
		buttonsPane.add(last);
		buttonsPane.add(next);
		buttonsPane.add(send);
		return buttonsPane;
	}
	
	/**内部类 扩展API的多选框 有int类型的value属性,代表答案传给服务器*/
	class Option extends JCheckBox{
		int value;
		public Option(int value,String text){
			super(text);
			this.value = value;
		}
	}
	
	/**
	 * 添加updateView(ExamInfo e,QuestionInfo qi)
	 * 把考试信息给本类当中的examInfo 
	 * 把QuestionInfo的信息给本类当中的questionArea
	 * 把第几题给考试界面当中左下角的questionCount
	 * 
	 * */
	public void updateView(ExamInfo examInfo, QuestionInfo questionInfo) {
		//更新考试信息
		this.examInfo.setText(examInfo.toString());
		//更新考题信息
		questionArea.setText(questionInfo.toString());
		//更新第几题信息
		questionCount.setText("第 "+questionInfo.getQuestionIndex()+" 题");
	}
	
	/**下一题按钮促使ExamFrame界面更新的内容(重载)*/
	public void updateView(QuestionInfo questionInfo) {
		
		//更新考题信息
		questionArea.setText(questionInfo.toString());
		//更新第几题信息
		questionCount.setText("第 "+questionInfo.getQuestionIndex()+" 题");
		updateOptions(questionInfo);//根据考题信息更新界面中用户选项部分
	}
	
	private void updateOptions(QuestionInfo questionInfo) {
		
		List<Integer> ans = questionInfo.getUserAnswers();
		for(int i=0;i<4;i++){
			if(ans.contains(options.get(i).value)){
				options.get(i).setSelected(true);
			}else{
				options.get(i).setSelected(false);
			}
			
		}
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}
	
	/**获取用户答案*/
	public List<Integer> getUserAnswers(){
		List<Integer> ans = new ArrayList<Integer>();
		for(Option option: options){
			if(option.isSelected()){
				ans.add(option.value);
			}
		}
		return ans;
	} 
}
