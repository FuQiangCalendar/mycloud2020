package com.atguigu.springcloud.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class CloudJobListener implements JobListener {

	@Override
	public String getName() {
		String name = this.getClass().getSimpleName();
		System.out.println("监听器的名称：" + name);
		return name;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		String name = context.getJobDetail().getKey().getName();
		String group = context.getJobDetail().getKey().getGroup();
		System.out.println("job的名称是：" + name + ", job的分组：" + group + ";Scheduler在JobDetail即将被执行");
		
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		String name = context.getJobDetail().getKey().getName();
		String group = context.getJobDetail().getKey().getGroup();
		System.out.println("job的名称是：" + name + ", job的分组：" + group + ";Scheduler在JobDetail即将被执行，但又被TriggerListener否决时会调用该方法" );
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		String name = context.getJobDetail().getKey().getName();
		String group = context.getJobDetail().getKey().getGroup();
		System.out.println("job的名称是：" + name + ", job的分组：" + group + ";Scheduler在JobDetail执行完成");
	}



}
