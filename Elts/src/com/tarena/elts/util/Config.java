package com.tarena.elts.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
	private Properties table = new Properties();
	/**给table赋值*/
	public Config(String fileName){
		try {
			table.load(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("没有找到配置文件！");//load抛出的异常
		} catch (IOException e) {
			e.printStackTrace();//FileInputStream抛出的异常
		}
	}
	//获取int/double/String类型的值
	public int getInt(String key){
		return Integer.parseInt(table.getProperty(key));
	}
	public double getDouble(String key){
		return Double.parseDouble(table.getProperty(key));
	}
	public String getString(String key){
		return table.getProperty(key);
	}
	
}
