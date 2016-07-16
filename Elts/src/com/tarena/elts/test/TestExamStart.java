package com.tarena.elts.test;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.service.ExamService;
import com.tarena.elts.service.ExamServiceImpl;
import com.tarena.elts.ui.ClientContext;
import com.tarena.elts.ui.ExamFrame;
import com.tarena.elts.ui.LoginFrame;
import com.tarena.elts.ui.MenuFrame;
import com.tarena.elts.util.Config;

public class TestExamStart {
	/**
	 * 测试开始考试功能
	 * 
	 * */
	public static void main(String[] args) {
		LoginFrame loginFrame = new LoginFrame();
		MenuFrame menuFrame = new MenuFrame();
		ExamFrame examFrame = new ExamFrame();
		
		ClientContext clientContext = new ClientContext();
		Config config = new Config("client.properties");
		EntityContext entityContext = new EntityContext(config);
		ExamService examService = new ExamServiceImpl();
		//把对象注入属性
		loginFrame.setClientContext(clientContext);
		menuFrame.setClientContext(clientContext);
		examFrame.setClientContext(clientContext);
		clientContext.setExamService(examService);
		clientContext.setMenuFrame(menuFrame);
		clientContext.setLoginFrame(loginFrame);
		clientContext.setExamFrame(examFrame);
		examService.setEntityContext(entityContext);
		
		clientContext.show();
		
	}
}
