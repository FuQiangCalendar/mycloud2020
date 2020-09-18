package com.atguigu.springcloud.exception;

public class Cloud2020Exception extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public Cloud2020Exception(String message){
		super(message);
	}
	
	public Cloud2020Exception(Throwable cause)
	{
		super(cause);
	}
	
	public Cloud2020Exception(String message,Throwable cause)
	{
		super(message,cause);
	}
}
