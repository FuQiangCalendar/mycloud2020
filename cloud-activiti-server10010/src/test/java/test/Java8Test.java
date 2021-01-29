package test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Java8Test {
	
	/**
	 * 日志操作对象
	 */
	private final static Logger LOGGER = LoggerFactory.getLogger(Java8Test.class);
	
	@Test
	public void test01 () {
		/**
		 * 生成流
		 * 在 Java 8 中, 集合接口有两个方法来生成流：
		 * stream() − 为集合创建串行流。
		 * parallelStream() − 为集合创建并行流。
		 */
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		LOGGER.info(strings.toString());
		LOGGER.info(filtered.toString());
		
		/**
		 * forEach
		 * Stream 提供了新的方法 'forEach' 来迭代流中的每个数据。以下代码片段使用 forEach 输出了10个随机数：
		 */
		Random random = new Random();
		random.ints().limit(10).forEach(System.out::println);
		
		/**
		 * map
		 * map 方法用于映射每个元素到对应的结果，以下代码片段使用 map 输出了元素对应的平方数：
		 */
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		// 获取对应的平方数
		List<Integer> squaresList = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
		LOGGER.info(squaresList.toString());
		
		/**
		 * filter
		 * filter 方法用于通过设置的条件过滤出元素。以下代码片段使用 filter 方法过滤出空字符串：
		 */
		List<String> strList = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl", null);
		// 获取空字符串的数量
		long count1 = strings.stream().filter(str -> str.isEmpty()).count();
		long count2 = strList.stream().filter(str -> StringUtils.isBlank(str)).count();
		LOGGER.info("strings空字符串个数>>>>" + count1);
		LOGGER.info("strList空值项个数>>>>" + count2);
		
		/**
		 * limit
		 * limit 方法用于获取指定数量的流。 以下代码片段使用 limit 方法打印出 10 条数据：
		 */
		random.ints().limit(10).forEach(System.out::println);
		
		/**
		 * sorted
		 * sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对输出的 10 个随机数进行排序：
		 */
		random.ints().limit(10).sorted().forEach(System.out::println);
		
		/**
		 * 并行（parallel）程序
		 * parallelStream 是流并行处理程序的代替方法。以下实例我们使用 parallelStream 来输出空字符串的数量：
		 */
		// 获取空字符串的数量
		long count = strings.parallelStream().filter(string -> string.isEmpty()).count();
		LOGGER.info("strings空字符串个数>>>>" + count);
		
		/**
		 * Collectors
		 * Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素。Collectors 可用于返回列表或字符串：
		 */
		System.out.println("筛选列表: " + filtered);
		String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
		System.out.println("合并字符串: " + mergedString);
		
		/**
		 * 统计
		 * 另外，一些产生统计结果的收集器也非常有用。它们主要用于int、double、long等基本类型上，它们可以用来产生类似如下的统计结果。
		 */
		List<Integer> numbers2 = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
		 
		IntSummaryStatistics stats = numbers2.stream().mapToInt((x) -> x).summaryStatistics();
		 
		System.out.println("列表中最大的数 : " + stats.getMax());
		System.out.println("列表中最小的数 : " + stats.getMin());
		System.out.println("所有数之和 : " + stats.getSum());
		System.out.println("平均数 : " + stats.getAverage());
	}
	
	@Test
	public void testNewDate () {
		LocalDate now = LocalDate.now();
		LocalTime now2 = LocalTime.now();
		LocalDateTime now3 = LocalDateTime.now();
		System.out.println(now);
		System.out.println(now2);
		System.out.println(now3);
		
		LocalDate of = LocalDate.of(2020, 5, 1);
		LocalTime of2 = LocalTime.of(12, 30, 30, 100);
		LocalDateTime of3 = LocalDateTime.of(2021, 5, 1, 12, 30, 30, 100);
		System.out.println(of);
		System.out.println(of2);
		System.out.println(of3);
		
		LocalDate plusDays = now.plusDays(10);
		System.out.println(plusDays);
		
		boolean leapYear = now.isLeapYear();
		System.out.println(leapYear);
		
		LocalDate of4 = LocalDate.of(2020, 5, 1);
		LocalDate of5 = LocalDate.of(1900, 5, 1);
		System.out.println(of4.isLeapYear());
		System.out.println(of5.isLeapYear());
	}
	
	@Test
	public void testOptional () {
		System.out.println(Optional.ofNullable(null).isPresent());
		System.out.println(Optional.ofNullable("123").isPresent());
	}
	
	
}
