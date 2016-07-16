package com.tarena.elts.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 封装考题信息     题号/分值/考题  包含题干和选项/答题区  保存考生选项
 *   
 * */
public class QuestionInfo {
	private int questionIndex = 5;//题号
	private int scorePer;//每题分数
	private Question question;//一道考题
	private List<Integer> userAnswers = new ArrayList<Integer>();
	
	
	public QuestionInfo() {
	}

	public QuestionInfo(int questionIndex, Question question) {
		this.questionIndex = questionIndex;
		this.question = question;
	}

	public int getQuestionIndex() {
		return questionIndex;
	}

	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}

	public int getScorePer() {
		return scorePer;
	}

	public void setScorePer(int scorePer) {
		this.scorePer = scorePer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public List<Integer> getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(List<Integer> userAnswers) {
		this.userAnswers = userAnswers;
	}
	
	/**显示每一个考题*/
	@Override
	public String toString() {
		return questionIndex+".("+scorePer+"分"+")"+question.toString();
	}
}
