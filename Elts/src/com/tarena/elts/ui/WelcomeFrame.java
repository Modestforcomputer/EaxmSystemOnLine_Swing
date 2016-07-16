package com.tarena.elts.ui;

import java.awt.Container;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class WelcomeFrame extends JFrame{
	
	public WelcomeFrame(){
		init();
	}

	private void init() {
		this.setTitle("欢迎使用本系统");
		this.setSize(550,325);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setContentPane(createImagePane());
	}

	/**设置欢迎界面的图片背景*/
	private Container createImagePane() {
		JPanel imageShow = new JPanel(new BorderLayout());
		JLabel image = new JLabel(new ImageIcon("resource/welcome.png"));
		imageShow.add(image);
		return imageShow;
	}
}
