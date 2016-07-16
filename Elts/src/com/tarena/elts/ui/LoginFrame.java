package com.tarena.elts.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

/**
 * 创建登录界面
 * 
 * */
@SuppressWarnings("serial")
public class LoginFrame extends JFrame{
	
	/**界面中的部件*/
	private JTextField idField = new JTextField();
	private JPasswordField pwdField = new JPasswordField();
	private JLabel message = new JLabel("登录试试",JLabel.CENTER);//提示信息
	/**控制器*/
	private ClientContext clientContext;
    
	public LoginFrame(){
		init();
	}

	/**初始化界面逻辑*/
	private void init() {
	    this.setTitle("登录系统");
	    this.setSize(300,220);
	    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    this.setLocationRelativeTo(null);
	    this.setContentPane(createContentPane());//设置主面板
	    this.setResizable(false);
	    this.addWindowListener(new WindowAdapter() {//更新窗口关闭功能
			public void windowClosing(WindowEvent e){
	    		clientContext.exit(LoginFrame.this);
	    	}
		});
	}
	
	/**创建主面板逻辑*/
	private Container createContentPane() {
		JPanel loginMainPane = new JPanel(new BorderLayout());
		loginMainPane.setBorder(new EmptyBorder(10,10,10,10));
		loginMainPane.add(new JLabel("登录系统",JLabel.CENTER),BorderLayout.NORTH);//北部
		loginMainPane.add(createCenterPane(),BorderLayout.CENTER);//中间 
		loginMainPane.add(createBtnPane(),BorderLayout.SOUTH);//南部
		return loginMainPane;
	}
	
	/**创建主面板的中间部分*/
	private Component createCenterPane() {
		JPanel centerPane = new JPanel(new BorderLayout());
		centerPane.add(createIdPwdPane(),BorderLayout.NORTH);//信息（用户名和密码）输入部分
		centerPane.add(message,BorderLayout.SOUTH);
		return centerPane;
	}
	
	/**中间部分输入区*/
	private Component createIdPwdPane() {
		JPanel inputPane = new JPanel(new GridLayout(2,1,0,15));
		inputPane.add(createIdPane());//用户名
		inputPane.add(createPwdPane());//密码
		return inputPane;
	}
	
	/**帐号输入框*/
	private Component createIdPane() {
		JPanel idPane = new JPanel(new BorderLayout());
		idPane.add(new JLabel("帐号:"),BorderLayout.WEST);
		idPane.add(idField,BorderLayout.CENTER);
		return idPane;
	}
	
	/**密码输入框*/
	private Component createPwdPane() {
		JPanel pwdPane = new JPanel(new BorderLayout());
		pwdPane.add(new JLabel("密码:"),BorderLayout.WEST);
		pwdPane.add(pwdField,BorderLayout.CENTER);
		return pwdPane;
	}

	/**创建主面板的南边按钮部分*/
	private Component createBtnPane() {
		JPanel buttonsPane = new JPanel(); 
		JButton loginButton = new JButton("登录");
		JButton cancelButton = new JButton("取消");
		loginButton.setFocusable(false);
		cancelButton.setFocusable(false);
		/**添加登录按钮监听事件*/
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.login();
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.exit(LoginFrame.this);//本身就为内部类，已经包含了this  
				               //不能用this表达LoginFrame本身  所以用LoginFrame.this
				
			}
		});
		buttonsPane.add(loginButton);
		buttonsPane.add(cancelButton);
		return buttonsPane;
	}
	
	/**供外界调用 对属性进行对象注入（IOC） 保证项目只有一个对象*/
	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}
	
	/**获取id输入框内容*/
	public String getUserId(){
		return idField.getText();
	}
	
	/**获取pwd输入框内容*/
	public String getPassword(){
		char[] temp = pwdField.getPassword();
		//返回一个字符串
		return new String(temp);
	}
	
	/**显示登录错误信息*/
	public void showMessage(String message) {
		this.message.setText(message);//更新登录的提示信息
	}
}
