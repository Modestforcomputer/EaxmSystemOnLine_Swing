package com.tarena.elts.test;

import com.tarena.elts.util.Config;

public class TestConfig {
	public static void main(String[] args){
		Config config = new Config("client.properties");
		System.out.println(config.getString("UserFile"));
	}
}
