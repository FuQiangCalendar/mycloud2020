package com.atguigu.springcloud.day01.java8;

@FunctionalInterface
public interface MyPredicate<T> {

	public boolean test(T t);
	
}
