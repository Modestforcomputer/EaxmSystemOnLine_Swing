package com.tarena.elts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.Question;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;

public class ExamServiceImpl implements ExamService {
	/**添加一个数据管理层的EntityContext对象 用于调用其方法获取数据*/
	private EntityContext entityContext;
	private User loginUser;
	private ExamInfo examInfo = new ExamInfo();
	
	/**创建一个包含所有考题的集合List<Question> paper*/
	private List<QuestionInfo> paper = new ArrayList<QuestionInfo>();
	
	private int score = 0;
	public void setEntityContext(EntityContext entityContext) {
		this.entityContext = entityContext;
	}

	/**判断登录成功或失败的逻辑*/
	@Override
	public User login(int id, String pwd) throws IdOrPwdException {
		/**
		 * 1.根据ID返回一个User对象
		 * 2.如果没有该ID 则无用户
		 * 3.如果有ID 判断密码  如果成功  执行下一步   并创建考卷
		 * 4.如果密码错误 则抛出IdOrPwdException
		 * 
		 * */
		loginUser = entityContext.getUserById(id);
		if(loginUser == null ){
			throw new IdOrPwdException("用户不存在");
		}
		if(loginUser.getPwd().equals(pwd)){
			return loginUser;
		}
		throw new IdOrPwdException("密码错误");
	}


	public ExamInfo getExamInfo() {
		return examInfo;
	}

	/**
	 * 
	 * 重写ExamService接口的start()
	 * 创建考卷  调用createPaper
	 * 创建ExamInfo对象  给各个examInfo赋值
	 * （科目/ 时间/ 总分/ 数量/  考生/ 登录用户）
	 * 返回ExamInfo对象
	 * 
	 * */
	@Override
	public ExamInfo start() {
		
		if(isFinished){
			throw new RuntimeException("你已完成本次考试！");
		}
		createPaper(entityContext.getQuestionCount());//创建试卷
		examInfo.setTitle(entityContext.getExamTitle());
		examInfo.setTimeLimit(entityContext.getTimeLimit());
		examInfo.setTotalScore(entityContext.getTotalScore());
		examInfo.setQuestionCount(entityContext.getQuestionCount());
		examInfo.setUser(loginUser);
		return examInfo;
	}

	/**
	 * 重写getQuestionInfo(int index)
	 * 返回一道考题
	 * */
	@Override
	public QuestionInfo getQuestionInfo(int index) {
		return paper.get(index);
	}
	
	/**
	 * 
	 * 添加一个私有的创建考卷的功能方法createPaper(int numbers)
	 * 根据参数随机获取entityContext里的考题集合  参数就是随机获取的个数
	 * 创建对应的QuestionInfo对象  添加题号/考题/分数
	 * 把一个个的QuestionInfo对象收集起来 作为考卷所有考题
	 * 
	 * */
	private void createPaper(int numbers){
		
		List<Question> questions = entityContext.getQuestions();
		
		Random ran = new Random();
		for(int i=0;i<numbers;i++){
			QuestionInfo questionInfo = new QuestionInfo();
			questionInfo.setQuestionIndex(i+1);
			questionInfo.setQuestion(questions.remove(ran.nextInt(questions.size())));
			paper.add(questionInfo);
		}
		setScore();
	}

	
	
	/**
	 * 添加一个私有的设置每个考题分数的方法setScore()
	 * 首先要知道考卷总分（ExamInfo中找）
	 * 要考题数量 
	 * 每道题的分数=总分/数量
	 * 把每道题目的分数分别给考题集合里的每一道题
	 * 
	 * */
	private void setScore(){
		int total = entityContext.getTotalScore();
		int num = entityContext.getQuestionCount();
		int scorePer = total/num;
		for(int i=0;i<paper.size();i++){
			paper.get(i).setScorePer(scorePer);
		}
	}

	/**重写接口的over方法
	 * 计算分数
	 * 
	 * */
	public int over() {
		for(QuestionInfo questionInfo: paper){
			Question question = questionInfo.getQuestion();
			if(questionInfo.getUserAnswers().equals(question.getAnswers())){
				score += questionInfo.getScorePer();
			}
		}
		isFinished = true;
		return score;
	}

	@Override
	public int getScore() {
		if(!isFinished){
			throw new RuntimeException("你还没参与考试！");
		}
		return score;
	}

	/**定义一个属性  判断是否考试结束  默认为false*/
	private boolean isFinished = false;
	@Override
	public String getExamRules() {
		return entityContext.getExamRules();
	}
	
}
