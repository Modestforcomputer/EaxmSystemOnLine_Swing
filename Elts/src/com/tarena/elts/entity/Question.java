package com.tarena.elts.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 考题类
 * 
 * 对象是题库中的每个考题
 * */
public class Question {
	
	public static final int LEVEL1 = 1;//难度级别范围
	public static final int LEVEL2 = 2;
	public static final int LEVEL3 = 3;
	public static final int LEVEL4 = 4;
	public static final int LEVEL5 = 5;
	public static final int LEVEL6 = 6;
	public static final int LEVEL7 = 7;
	public static final int LEVEL8 = 8;
	public static final int LEVEL9 = 9;
	public static final int LEVEL10 = 10;
	
	public static final int SINGLE_SELECTION = 0;//单选标志
	public static final int MULTI_SELECTION = 1;//多选标志
	
	private String title;//题干
	private int level;//难度级别
	private int id;//题号
	private int type;//题型（单选/多选）
	private List<Integer> answers = new ArrayList<Integer>();//答案
	private List<String> options = new ArrayList<String>();//选项
	
	public Question() {
	}
	
	/**
	 * 设置考题
	 * 
	 * */
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}
	
	public void setOptions(List<String> selections) {
		this.options = selections;
	}
	
	/**
	 * 获取考题信息
	 * 
	 * */
	public String getTitle() {
		return title;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public List<Integer> getAnswers() {
		return answers;
	}
	
	public List<String> getOptions() {
		return options;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		//题干/选项/正确答案
		StringBuffer st = new StringBuffer();
		st.append(title);
		st.append("\n");
		for(int i=0;i<options.size();i++){
			st.append((char)(i+'A')).append(".")
			.append(options.get(i)).append("\n");
		}
		st.append("正确答案: ");
		for(int i=0; i<answers.size();i++){
			st.append((char)(answers.get(i)+'A')).
			append(" ");
		}
		st.append("\n");
		return st.toString();
	}
}

