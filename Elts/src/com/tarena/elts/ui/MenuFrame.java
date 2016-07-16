package com.tarena.elts.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

/**
 * 创建主菜单界面
 * 
 * */
@SuppressWarnings("serial")
public class MenuFrame extends JFrame{
	
	/**界面中的部件*/
	private JLabel welcomeInfo = new JLabel("",JLabel.CENTER);
	/**控制器  供该类调用*/
	private ClientContext clientContext = new ClientContext();
	
	public MenuFrame(){
		init();
	}
	
	/**初始化主菜单界面*/
	private void init() {
		this.setSize(600,460);
		this.setTitle("在线考试系统");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(createContentPane());//主面板
		this.addWindowListener(new WindowAdapter() {//更新窗口关闭功能
			public void windowClosing(WindowEvent e){
	    		clientContext.exit(MenuFrame.this);
	    	}
		});
	}
	
	/**创建主面板逻辑*/
	private Container createContentPane() {
		JPanel menuMainPane = new JPanel(new BorderLayout());
		menuMainPane.add(new JLabel(new ImageIcon("resource/title.png")),BorderLayout.NORTH);//主面板北部
		menuMainPane.add(createCenterPane(),BorderLayout.CENTER);//主面板中间面板
		menuMainPane.add(new JLabel("XXX-版权所有，盗版必究",JLabel.RIGHT),BorderLayout.SOUTH);//主面板南部
		return menuMainPane;
	}
	
	/**创建主面板的中间部分*/
	private Component createCenterPane() {
		JPanel centerPane = new JPanel(new BorderLayout());
		centerPane.add(welcomeInfo,BorderLayout.NORTH);
		centerPane.add(createBtnPane(),BorderLayout.CENTER);//按钮面板
		return centerPane;
	}
	
	/**创建主面板中间部分的按钮面板*/
	private Component createBtnPane() {
		JPanel buttonsPane = new JPanel();
		JButton start = createImageIconButton("resource/start.png","开始考试");
		JButton score = createImageIconButton("resource/score.png","考试分数");
		JButton rule = createImageIconButton("resource/rule.png","考试规则");
		JButton exit = createImageIconButton("resource/exit.png","退出系统");
		/**给开始考试按钮添加事件  该事件触发后调用clientContext的start()*/
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.start();
			}
		});
		score.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.getScore();
			}
		});
		rule.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.getExamRules();
			}
		});
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.exit(MenuFrame.this);
			}
		});
		start.setFocusable(false);
		score.setFocusable(false);
		rule.setFocusable(false);
		exit.setFocusable(false);
		buttonsPane.add(start);
		buttonsPane.add(score);
		buttonsPane.add(rule);
		buttonsPane.add(exit);
		return buttonsPane;
	}
	
	/**设置带图标的按钮*/
	private JButton createImageIconButton(String icondir, String message) {
		ImageIcon icon = new ImageIcon(icondir);
		JButton button = new JButton(message,icon);
		//将文本垂直方向底部对齐
		button.setVerticalTextPosition(JButton.BOTTOM);
		//将文本水平方向居中对齐
		button.setHorizontalTextPosition(JButton.CENTER);
		return button;
	}
	
	/**更新菜单界面的方法*/
	public void updateView(String userName){
		welcomeInfo.setText("欢迎" + userName + " 同学！");
	}
	
    /**添加一个ClientContext的set注入方法*/
	public void setClientContext(ClientContext clientContext2) {
		this.clientContext = clientContext2;
	}
}
