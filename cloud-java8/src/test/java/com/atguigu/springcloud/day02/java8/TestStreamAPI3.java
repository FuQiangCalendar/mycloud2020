package com.atguigu.springcloud.day02.java8;

import com.atguigu.springcloud.day02.java8.Employee.Status;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;


public class TestStreamAPI3 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 79, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Status.BUSY)
	);
	
	//3. 终止操作
	/*
		归约
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void test1(){
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
			.reduce(0, (x, y) -> x + y);
		
		System.out.println(sum);
		
		System.out.println("----------------------------------------");
		
		Optional<Double> op = emps.stream()
			.map(Employee::getSalary)
			.reduce(Double::sum);
		
		System.out.println(op.get());
	}
	
	//需求：搜索名字中 “六” 出现的次数
	@Test
	public void test2(){
		Optional<Integer> sum = emps.stream()
			.map(Employee::getName)
			.flatMap(TestStreamAPI1::filterCharacter)
			.map((ch) -> {
				if(ch.equals('六'))
					return 1;
				else 
					return 0;
			}).reduce(Integer::sum);
		
		System.out.println(sum.get());
	}
	
	//collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
	@Test
	public void test3(){
		List<String> list = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toList());
		
		list.forEach(System.out::println);
		
		System.out.println("----------------------------------");
		
		Set<String> set = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toSet());
		
		set.forEach(System.out::println);

		System.out.println("----------------------------------");
		
		HashSet<String> hs = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toCollection(HashSet::new));
		
		hs.forEach(System.out::println);
	}
	
	@Test
	public void test4(){
		Optional<Double> max = emps.stream()
			.map(Employee::getSalary)
			.collect(Collectors.maxBy(Double::compare));
		
		System.out.println(max.get());
		
		Optional<Employee> op = emps.stream()
			.collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
		
		System.out.println(op.get());
		
		Double sum = emps.stream()
			.collect(Collectors.summingDouble(Employee::getSalary));
		
		System.out.println(sum);
		
		Double avg = emps.stream()
			.collect(Collectors.averagingDouble(Employee::getSalary));
		
		System.out.println(avg);
		
		Long count = emps.stream()
			.collect(Collectors.counting());
		
		System.out.println(count);
		
		System.out.println("--------------------------------------------");
		
		DoubleSummaryStatistics dss = emps.stream()
			.collect(Collectors.summarizingDouble(Employee::getSalary));
		
		System.out.println(dss.getMax());
	}
	
	//分组
	@Test
	public void test5(){
		Map<Status, List<Employee>> map = emps.stream()
			.collect(Collectors.groupingBy(Employee::getStatus));
		
		System.out.println(map);
	}
	
	//多级分组
	@Test
	public void test6(){
		Map<Status, Map<String, List<Employee>>> map = emps.stream()
			.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
				if(e.getAge() >= 60)
					return "老年";
				else if(e.getAge() >= 35)
					return "中年";
				else
					return "成年";
			})));
		
		System.out.println(map);
	}
	
	//分区
	@Test
	public void test7(){
		Map<Boolean, List<Employee>> map = emps.stream()
			.collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
		
		System.out.println(map);
	}
	
	//
	@Test
	public void test8(){
		String str = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.joining("," , "----", "----"));
		
		System.out.println(str);
	}
	
	@Test
	public void test9(){
		Optional<Double> sum = emps.stream()
			.map(Employee::getSalary)
			.collect(Collectors.reducing(Double::sum));
		
		System.out.println(sum.get());
	}

	/**
	* 合并两个List 中对象某个值相同，合并对象 相当与BeanUtils.copyProperties(org,dest)
	*/
	@Test
	public void test10 () {
		Map<String,Object> map1 = new HashMap<>();
		map1.put("patentNumber","123");
		map1.put("id","1");
		map1.put("tradingStatus",null);

		Map<String,Object> map2 = new HashMap<>();
		map2.put("patentNumber","123");
		map2.put("remarks","");
		map2.put("caseName","秀啊");
		map2.put("patentStatus","1");
		map2.put("checkInDeadline",null);
		map2.put("transferAgencyFee","1");
		map2.put("commissionAmount","200");

		List<Map<String,Object>> list1 = new ArrayList<>();
		list1.add(map1);
		List<Map<String,Object>> list2 = new ArrayList<>();
		list2.add(map2);

		List<Map<String,Object>> lists = new ArrayList<>();
		String mergeKey = "patentNumber";
		lists.addAll(list1);
		lists.addAll(list2);

		Set set = new HashSet<>();

		Map<String, List<Map<String, Object>>> collect1 = lists.stream()
				.filter(map -> map.get(mergeKey) != null)

				.collect(Collectors.groupingBy(o -> {//暂存所有key
					set.addAll(o.keySet());//按mergeKey分组
					return o.get(mergeKey).toString();
				}));
		System.out.println(collect1);

		//详细版
		List<Map> collect = lists.stream()
				.filter(map -> map.get(mergeKey) != null)

				.collect(Collectors.groupingBy(o -> {//暂存所有key
					set.addAll(o.keySet());//按mergeKey分组
					return o.get(mergeKey).toString();
				}))
				.entrySet().stream().map(o -> {//合并
					Map map = o.getValue().stream().flatMap(m -> {
						return m.entrySet().stream();
					}).collect(HashMap::new, (m,v) -> m.put(v.getKey(), v.getValue()), HashMap::putAll); // 解决value值为null，导致的NPE问题
//							.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b)); // 此方法会因value的值为null，而报NPE
					//为没有的key赋值0
					set.stream().forEach(k -> {
						if (!map.containsKey(k)) map.put(k, 0);
					});
					return map;

				}).collect(Collectors.toList());


		System.out.println("详细版>>>" + collect);

		//简化版
		collect = lists.stream()
				.collect(Collectors.groupingBy(g -> g.get("patentNumber").toString())).entrySet().stream()
				.map(m -> m.getValue().stream().flatMap(o -> o.entrySet().stream())
						.collect(HashMap::new,(s,v) ->s.put(v.getKey(),v.getValue()),HashMap::putAll)).collect(Collectors.toList());
		System.out.println("简化版>>>" + collect);
	}
}
