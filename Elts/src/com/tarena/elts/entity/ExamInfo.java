package com.tarena.elts.entity;

/**
 * 封装考试信息   考试科目/时间/题量/总分/考生
 * 
 * */
public class ExamInfo {
	
	private String title;//考试科目
	private int    timeLimit;//考试时间
	private int    questionCount;//题量
	private int    totalScore;//总分
	private User   user;//考生
	
	public ExamInfo() {
	}
	
	/**
	 * 设置考试信息
	 * 
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}
	
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * 获取考试信息
	 * 
	 * */
	public String getTitle() {
		return title;
	}
	
	public int getTimeLimit() {
		return timeLimit;
	}
	
	public int getQuestionCount() {
		return questionCount;
	}
	
	public int getTotalScore() {
		return totalScore;
	}
	
	public User getUser() {
		return user;
	}

	@Override
	public String toString() {
		StringBuffer info = new StringBuffer();
		info.append("姓名:").append(user.getName()).
		append("  编号:").append(user.getId()).append("  科目:").
		append(title).append("  时间:").append(timeLimit).
		append("  总分:").append(totalScore).append("  题量:").append(questionCount);
		return info.toString();
	}
}
