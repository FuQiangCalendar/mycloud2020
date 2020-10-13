package com.atguigu.springcloud.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class HelloJobTest {
	
	/**
	 * 
	 * @Title:	main
	 * @Description:	Quartz API的关键接口是：
	 * Scheduler - 与调度程序交互的主要API。
	 * Job - 你想要调度器执行的任务组件需要实现的接口
	 * JobDetail - 用于定义作业的实例。
	 * Trigger（即触发器） - 定义执行给定作业的计划的组件。
	 * JobBuilder - 用于定义/构建 JobDetail 实例，用于定义作业的实例。
	 * TriggerBuilder - 用于定义/构建触发器实例。
	 * Scheduler 的生命期，从 SchedulerFactory 创建它时开始，到 Scheduler 调用shutdown() 方法时结束；
	 * Scheduler 被创建后，可以增加、删除和列举 Job 和 Trigger，以及执行其它与调度相关的操作（如暂停 Trigger）。
	 * 但是，Scheduler 只有在调用 start() 方法后，才会真正地触发 trigger（即执行 job）
	 * @param:	@param args
	 * @param:	@throws SchedulerException
	 * @return:	void
	 * @author:	FuQiang
	 * @date:	2020年10月13日 下午3:31:32
	 * @throws
	 */
    public static void main(String[] args) throws SchedulerException {
        
        //创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");
        
        //创建一个Trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                // 设置为3秒中一次
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3) 
                        .repeatForever()).build();
        trigger.getJobDataMap().put("t2", "tv2");
        
        //创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .usingJobData("j1", "jv1")
                    .withIdentity("myjob", "mygroup").build();
        job.getJobDataMap().put("j2", "jv2");
        
        //注册trigger并启动scheduler
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
        
    }

}