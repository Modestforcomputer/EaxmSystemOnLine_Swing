package com.tarena.elts.service;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;

/**
 * 
 * 业务模型
 * 
 * */
public interface ExamService {
	/**用户登录功能*/
	User login(int id,String pwd)throws IdOrPwdException;
	public void setEntityContext(EntityContext entityContext);
	
	/**添加开始考试的方法ExamInfo start()*/
	public ExamInfo start();
	
	/**获得考试信息*/
	public ExamInfo getExamInfo();
	
	/**获得考题信息的方法 getQuestionInfo(int index)*/
	public QuestionInfo getQuestionInfo(int index);
	
	/**考试结束
	 * @param timers */
	public int over();
	public int getScore();
	
	/**获取考试规则*/
	public String getExamRules();
}
