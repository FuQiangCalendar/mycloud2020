package com.atguigu.springcloud.nio01;

import java.nio.charset.Charset;

public class SystemCharsetTest {
	public static void main(String[] args) {
		Charset defaultCharset = Charset.defaultCharset();
		System.out.println("defaultCharset.displayName()>>" + defaultCharset.displayName() 
			+ "；defaultCharset.toString()>>" + defaultCharset.toString());
	}
}
