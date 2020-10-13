package com.atguigu.springcloud.exception;

public class CloudException extends RuntimeException {

	/**
	 * @Fields	serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 * @author:	FuQiang
	 * @date:	2020年10月13日 下午2:59:29
	 */
	private static final long serialVersionUID = 1L;
	
	public CloudException(String message){
		super(message);
	}
	
	public CloudException(Throwable cause)
	{
		super(cause);
	}
	
	public CloudException(String message,Throwable cause)
	{
		super(message,cause);
	}

}
