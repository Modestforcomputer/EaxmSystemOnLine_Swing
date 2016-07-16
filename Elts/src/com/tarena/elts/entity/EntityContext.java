package com.tarena.elts.entity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tarena.elts.util.Config;

/**数据管理 实体对象与数据库之间  值对象与项目之间的上下文关系*/
public class EntityContext {
	
	private Config config;
	
	//通过Key找到value，更容易通过ID找到用户
	private HashMap<Integer,User> users = new HashMap<Integer,User>();
	//所有考题的实例集合 每个元素就是一个考题
	private List<Question> questions = new ArrayList<Question>();
	//存储考试规则
	private String rules = new String();
	
	public EntityContext(Config config) {
		this.config = config;
		loadUser(config.getString("UserFile"));
		loadQuestion(config.getString("QuestionFile"));
		loadExamRule(config.getString("ExamRuleFile"));
	}

	/**
	 * 加载用户 从数据文件里去加载
	 * 1.根据文件名字找到相应文件
	 * 2.创建文件输入流 到系统里
	 * 3.读取每行数据
	 * 4.分析数据  
	 * 5.根据分析数据的结果创建User
	 * 6.把User给users
	 * 
	 * */
	/**加载用户*/
	private void loadUser(String fileName){
		//根据fileName创建的一个输入流
		try {
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
					new FileInputStream(fileName),"utf-8"));
			String line = null;
			while((line = in.readLine())!= null){
				line.trim();//去除左右两端的空格
				//分析line转换为User
				User user = paseUser(line);
				//把User对象给users
				users.put(user.getId(), user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**分析用户文件中的数据并转换为User*/
	private User paseUser(String line) {
		String[] data = line.split(":");
		//创建用户
		User user = new User();
		//将读取到的行数据分析后赋值
		user.setId(Integer.parseInt(data[0]));
		user.setName(data[1]);
		user.setPwd(data[2]);
		user.setPhone(data[3]);
		user.setMail(data[4]);
		return user;
	}
	
	/**
	 * 加载考题 从数据文件里去加载
	 * 1.根据文件名字找到相应文件
	 * 2.创建文件输入流 到系统里
	 * 3.读取每行数据
	 * 4.分析数据  
	 * 5.根据分析数据的结果创建Question
	 * 6.把Question给questions
	 * 
	 * */
	/**加载考题*/
	private void loadQuestion(String fileName) {
		try{
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(fileName),"utf-8"));
			String line = new String();
			while((line = in.readLine()) != null){
				if(line.startsWith("#") || line.equals(" ")){
					continue;
				}
				//分析数据 转换为Question
				Question question = paseQuestion(line,in);
				//把对象给questions
				questions.add(question);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**分析考题文件中的数据并转换成Question*/
	private Question paseQuestion(String line, BufferedReader in) throws IOException {
		//把line解析成答案和难度等级
		String[] oneLine = line.split("[@,][a-z]+=");
		//掐取正确答案(oneLine[0]为split留下的空格)
		String[] answer = oneLine[1].split("/");
		//将答案存入集合
		List<Integer> answers = new ArrayList<Integer>();
		for(String ans : answer){//只能用于遍历数组
			answers.add(Integer.parseInt(ans));
		}
		//掐取难度等级
		String level = oneLine[2];
		//读取题干
		String title = in.readLine();//调用该函树的函数已经使用try-catch,所以此时异常只用抛出去
		//读取选项
		List<String> selections = new ArrayList<String>();
		for(int i=0;i<4;i++){
			selections.add(in.readLine());
		}
		Question question = new Question();
		question.setAnswers(answers);
		question.setLevel(Integer.parseInt(level));
		question.setTitle(title);
		question.setOptions(selections);
		return  question;
	}
	
	/**
	 * 加载考试规则 从数据文件里去加载
	 * 1.根据文件名字找到相应文件
	 * 2.创建文件输入流 到系统里
	 * 3.读取每行数据
	 * 4.把每行数据给rules
	 * 
	 * */
	/**加载考试规则*/
	private void loadExamRule(String fileName) {
		try {
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new InputStreamReader
					(new FileInputStream(fileName),"utf-8"));
			
			String line = new String();
			StringBuffer tempRules = new StringBuffer();
			while((line = in.readLine()) != null){
				tempRules.append(line);
				tempRules.append("\n");
				tempRules.append("\n");
			}
			rules = tempRules.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**获取考试规则*/
	public String getExamRules(){
		return rules;
	}
	
	/**根据ID返回一个User*/
	public User getUserById(int id){
		return users.get(id);
	}

	/**返回考题文件中所有考题*/
	public List<Question> getQuestions() {
		return questions;
	}
	
	/**获取考试科目/总分/时间/考题数量*/
	public String getExamTitle(){
		return config.getString("ExamTitle");
	}
	
	public int getTotalScore(){
		return config.getInt("TotalScore");
	}
	
	public int getTimeLimit(){
		return config.getInt("TimeLimit");
	}
	
	public int getQuestionCount(){
		return config.getInt("QuestionCount");
	}
}
