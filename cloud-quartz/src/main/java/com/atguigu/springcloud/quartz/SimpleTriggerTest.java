package com.atguigu.springcloud.quartz;

//import static org.quartz.TriggerBuilder.*;

import java.util.Calendar;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
//import static org.quartz.SimpleScheduleBuilder.*;
//import static org.quartz.DateBuilder.*;

public class SimpleTriggerTest {
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
        Calendar calendar= Calendar.getInstance();
        //创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("skey", "svalue");
        
        //创建一个SimpleTrigger 指定时间开始触发
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger() 
                .withIdentity("trigger1", "group1")
                .startAt(calendar.getTime())                     // some Date 
                .forJob("job1", "group1")                 // identify job with name, group strings
                .build();
        
        
        /*Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .usingJobData("t1", "tv1")
                // 设置为3秒中一次
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(3) 
                        .repeatForever()).build();*/
        trigger.getJobDataMap().put("t2", "tv2");
        
        //创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .usingJobData("j1", "jv1")
                    .withIdentity("myjob", "mygroup").build();
        job.getJobDataMap().put("j2", "jv2");
        
        //指定时间触发，每隔10秒执行一次，重复10次：
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .usingJobData("t1", "tv1")
                .startAt(calendar.getTime())  // if a start time is not given (if this line were omitted), "now" is implied
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(10)
                    .withRepeatCount(10)) // note that 10 repeats will give a total of 11 firings
                .forJob(job) // identify job with handle to its JobDetail itself                   
                .build();
        
        //5分钟以后开始触发，仅执行一次：
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger() 
                .withIdentity("trigger5", "group1")
                .startAt(DateBuilder.futureDate(5, IntervalUnit.MINUTE)) // use DateBuilder to create a date in the future
                .forJob(job) // identify job with its JobKey
                .build();
        
      //立即触发，每个5分钟执行一次，直到22:00：
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(5)
                    .repeatForever())
                .endAt(DateBuilder.dateOf(22, 0, 0))
                .build();
        		
        //建立一个触发器，将在下一个小时的整点触发，然后每2小时重复一次：		
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger8") // because group is not specified, "trigger8" will be in the default group
                .startAt(DateBuilder.evenHourDate(null)) // get the next even-hour (minutes and seconds zero ("00:00"))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInHours(2)
                    .repeatForever())
                // note that in this example, 'forJob(..)' is not called which is valid 
                // if the trigger is passed to the scheduler along with the job  
                .build();
        
        //SimpleTrigger Misfire策略 立即触发  每个5分钟执行一次
        /**
         * SimpleTrigger的Misfire策略(MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY)常量：
         * 
         * MISFIRE_INSTRUCTION_FIRE_NOW  1 指示<code> {@ link Scheduler} </ code>发生误触发
         * 在这种情况下，<code> {@ link SimpleTrigger} </ code>希望被解雇现在由<code> Scheduler </ code>调度。
         * <i>注意：</ i>该指令通常仅用于“单次”（非重复）触发器。 如果用于带有
         * 重复计数> 0，则等效于指令<code> {@ link #MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT}
         * 
         * MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT  2  指示<code> {@ link Scheduler} </ code>发生误触发情况下，<code> {@ link SimpleTrigger} </ code>希望成为
         * 重新安排为“现在”（即使相关的<code> {@ link日历} </ code>不包括“ now”），重复计数保持原样。 这确实服从
         * 但是<code> Trigger </ code>结束时间，因此如果“ now”在结束时，<code> Trigger </ code>不会再次触发。
         * <i>注意：</ i>使用此指令会导致触发器“忘记”最初设置时的开始时间和重复计数（此仅在您出于某种原因想知道什么时才是问题原始值是在稍后的时间）。
         * 
         * MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT  3  指示<code> {@ link Scheduler} </ code>发生误触发情况下，<code> {@ link SimpleTrigger} </ code>希望成为
         * 重新安排为“现在”（即使相关的<code> {@ link日历} </ code>排除“现在”），并将重复计数设置为原来的值（如果已设置）
         * 不要错过任何射击。这确实遵守了<code> Trigger </ code>的结束时间但是，如果'now'在结束时间之后，则<code> Trigger </ code>将
         * 不会再次开火。<i>注意：</ i>使用此指令会导致触发器“忘记”
         * 最初设置时的开始时间和重复计数。相反，触发器上的重复计数将更改为
         * 剩余的重复计数为（如果您需要原因是希望能够分辨出某些原始值晚点）。
         * <i>注意：</ i>该指令可能导致<code> Trigger </ code>触发“现在”后进入“完成”状态，如果所有错过的重复发射时间。
         * 
         * MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT  4  指示<code> {@ link Scheduler} </ code>发生误触发
         * 情况下，<code> {@ link SimpleTrigger} </ code>希望成为
         * 重新安排到“现在”之后的下一个安排的时间-考虑到 为所有关联的<code> {@ link日历} </ code>帐户，并使用
         * 如果没有错过任何一次射击，则将重复计数设置为原来的水平。
         * <i>注意/警告：</ i>此指令可能导致<code> Trigger </ code>如果错过所有火灾时间，则直接进入“完成”状态。
         * 
         * MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_EXISTING_COUNT  5  发生误触发情况下,希望成为重新安排到“现在”之后的下一个安排的时间-考虑到为所有关联的
         * <code> {@ link日历} </ code>帐户，并使用重复计数保持不变。<i>注意/警告：</ i>此指令可能导致<code> Trigger </ code>
         * 如果触发器的结束时间直接进入“完成”状态
         */
        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMinutes(5)
                    .repeatForever()
                    .withMisfireHandlingInstructionNextWithExistingCount())
                .build();
        
        //注册trigger并启动scheduler
        scheduler.scheduleJob(job,trigger);
        scheduler.start();
        
    }
}
