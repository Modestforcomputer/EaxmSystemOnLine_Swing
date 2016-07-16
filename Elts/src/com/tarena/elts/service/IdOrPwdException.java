package com.tarena.elts.service;
/**自定义异常*/
@SuppressWarnings("serial")
public class IdOrPwdException extends Exception{

	public IdOrPwdException() {
	}
	public IdOrPwdException(String message) {
		super(message);
	}
	
}
