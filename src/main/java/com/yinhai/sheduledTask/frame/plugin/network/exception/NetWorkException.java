package com.yinhai.sheduledTask.frame.plugin.network.exception;

/**
 * 远程调用时另一方抛出的异常
 * @author zrc
 *
 */
public class NetWorkException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	
	NetWorkException() {
	}

	public NetWorkException(String s) {
		super(s);
		errorMessage = s;
	}

	public NetWorkException(String s, String s1) {
		super(s);
		errorMessage = s;
		fieldName = s1;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessage() {
		return "远程方法调用失败 ，失败原因："+errorMessage;
	}

	private String errorMessage;
	private String fieldName;
}
