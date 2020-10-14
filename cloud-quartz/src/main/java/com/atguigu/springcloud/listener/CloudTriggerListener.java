package com.atguigu.springcloud.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

public class CloudTriggerListener implements TriggerListener {

	@Override
	public String getName() {
		String name = this.getClass().getSimpleName();
		System.out.println("监听器的名称：" + name);
		return name;
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		String name = context.getJobDetail().getKey().getName();
		String group = context.getJobDetail().getKey().getGroup();
		System.out.println("job的名称是：" + name + ", job的分组：" + group + ";Scheduler在JobDetail即将被执行");
		System.out.println("trigger的名称是：" + trigger.getKey().getName() + ", trigger的分组：" + trigger.getKey().getGroup() + ";被触发");
		trigger.getStartTime();
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		System.out.println("trigger的名称是：" + trigger.getKey().getName() + ", trigger的分组：" + trigger.getKey().getGroup() + ";没有被触发");
		return true;//表示不会执行job的方法
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		System.out.println("trigger的名称是：" + trigger.getKey().getName() + ", trigger的分组：" + trigger.getKey().getGroup() + ";错过触发");

	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		//trigger被触发，并且完成job的执行时，schedule 调用此方法
		System.out.println("trigger的名称是：" + trigger.getKey().getName() + ", trigger的分组：" + trigger.getKey().getGroup() + ";完成之后触发");

	}

}
