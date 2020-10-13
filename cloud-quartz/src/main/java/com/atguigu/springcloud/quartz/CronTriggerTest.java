package com.atguigu.springcloud.quartz;

/**
 * 
 * @ClassName:	CronTriggerTest
 * @Description: CronTrigger通常比Simple Trigger更有用，如果您需要基于日历的概念而不是按照SimpleTrigger的精确指定间隔进行重新启动的作业启动计划。
 * 使用CronTrigger，您可以指定号时间表，例如“每周五中午”或“每个工作日和上午9:30”，甚至“每周一至周五上午9:00至10点之间每5分钟”和1月份的星期五“。
 * 即使如此，和SimpleTrigger一样，CronTrigger有一个startTime，它指定何时生效，以及一个（可选的）endTime，用于指定何时停止计划。
 * Cron-Expressions用于配置CronTrigger的实例。Cron Expressions是由七个子表达式组成的字符串，用于描述日程表的各个细节。这些子表达式用空格分隔，并表示：
 * Seconds
 * Minutes
 * Hours
 * Day-of-Month
 * Month
 * Day-of-Week
 * Year (optional field)
 * 一个完整的Cron-Expressions的例子是字符串“0 0 12？* WED“ - 这意味着”每个星期三下午12:00“。
 * 单个子表达式可以包含范围和/或列表。例如，可以用“MON-FRI”，“MON，WED，FRI”或甚至“MON-WED，SAT”代替前一个（例如“WED”）示例中的星期几字段。
 * 通配符（' '字符）可用于说明该字段的“每个”可能的值。因此，前一个例子的“月”字段中的“”字符仅仅是“每个月”。因此，“星期几”字段中的“*”显然意味着“每周的每一天”。
 * 所有字段都有一组可以指定的有效值。这些值应该是相当明显的 - 例如秒和分钟的数字0到59，数小时的值0到23。日期可以是1-31的任何值，但是您需要注意在给定的月份中有多少天！月份可以指定为0到11之间的值，或者使用字符串JAN，FEB，MAR，APR，MAY，JUN，JUL，AUG，SEP，OCT，NOV和DEC。星期几可以指定为1到7（1 =星期日）之间的值，或者使用字符串SUN，MON，TUE，WED，THU，FRI和SAT。
 * '/'字符可用于指定值的增量。例如，如果在“分钟”字段中输入“0/15”，则表示“每隔15分钟，从零开始”。如果您在“分钟”字段中使用“3/20”，则意味着“每隔20分钟，从三分钟开始” - 换句话说，它与“分钟”中的“3,243,43”相同领域。请注意“ / 35”的细微之处并不代表“每35分钟” - 这意味着“每隔35分钟，从零开始” - 或者换句话说，与指定“0,35”相同。
 * '？' 字符是允许的日期和星期几字段。用于指定“无特定值”。当您需要在两个字段中的一个字段中指定某个字符而不是另一个字段时，这很有用。请参阅下面的示例（和CronTrigger JavaDoc）以进行说明。
 * “L”字符允许用于月日和星期几字段。这个角色对于“最后”来说是短暂的，但是在这两个领域的每一个领域都有不同的含义。例如，“月”字段中的“L”表示“月的最后一天” - 1月31日，非闰年2月28日。如果在本周的某一天使用，它只是意味着“7”或“SAT”。但是如果在星期几的领域中再次使用这个值，就意味着“最后一个月的xxx日”，例如“6L”或“FRIL”都意味着“月的最后一个星期五”。您还可以指定从该月最后一天的偏移量，例如“L-3”，这意味着日历月份的第三个到最后一天。当使用'L'选项时，重要的是不要指定列表或值的范围，因为您会得到混乱/意外的结果。
 * “W”用于指定最近给定日期的工作日（星期一至星期五）。例如，如果要将“15W”指定为月日期字段的值，则意思是：“最近的平日到当月15日”。
 * '＃'用于指定本月的“第n个”XXX工作日。例如，“星期几”字段中的“6＃3”或“FRI＃3”的值表示“本月的第三个星期五”。
 * 以下是一些表达式及其含义的更多示例 - 您可以在JavaDoc中找到更多的org.quartz.CronExpression
 * 
 * Cron Expressions示例
 * CronTrigger示例1 - 创建一个触发器的表达式，每5分钟就会触发一次
 * “0 0/5 * * *？”
 * CronTrigger示例2 - 创建触发器的表达式，每5分钟触发一次，分钟后10秒（即上午10时10分，上午10:05:10等）。
 * “10 0/5 * * *？”
 * CronTrigger示例3 - 在每个星期三和星期五的10:30，11:30，12:30和13:30创建触发器的表达式。
 * “0 30 10-13？* WED，FRI“
 * CronTrigger示例4 - 创建触发器的表达式，每个月5日和20日上午8点至10点之间每半小时触发一次。请注意，触发器将不会在上午10点开始，仅在8:00，8:30，9:00和9:30
 * “0 0/30 8-9 5,20 *？”
 * 请注意，一些调度要求太复杂，无法用单一触发表示 - 例如“每上午9:00至10:00之间每5分钟，下午1:00至晚上10点之间每20分钟”一次。
 * 在这种情况下的解决方案是简单地创建两个触发器，并注册它们来运行相同的作业。
 * @author:	FuQiang
 * @date:	2020年10月13日 下午4:16:14
 */
import static org.quartz.TriggerBuilder.*;

import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.matchers.KeyMatcher;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

import com.atguigu.springcloud.listener.CloudJobListener;

import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.DateBuilder.*;
import static org.quartz.impl.matchers.KeyMatcher.*;
import static org.quartz.impl.matchers.GroupMatcher.*;
import static org.quartz.impl.matchers.AndMatcher.*;
import static org.quartz.impl.matchers.OrMatcher.*;
import static org.quartz.impl.matchers.EverythingMatcher.*;
import static org.quartz.impl.matchers.NotMatcher.*;
import static org.quartz.impl.matchers.StringMatcher.*;
import static org.quartz.impl.matchers.NameMatcher.*;

public class CronTriggerTest {

	public static void main(String[] args) throws SchedulerException {
		
		//创建一个job
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                    .usingJobData("j1", "jv1")
                    .withIdentity("myJob", "mygroup")
                    .build();
        job.getJobDataMap().put("j2", "jv2");
		
		//建立一个触发器，每隔一分钟，每天上午8点至下午5点之间：
        Trigger trigger = newTrigger()
			    .withIdentity("trigger3", "mygroup")
			    .withSchedule(cronSchedule("0 0/2 8-19 * * ?"))
			    .forJob("myJob", "mygroup")
			    .build();
		
		//建立一个触发器，将在上午10:42每天发射：
		/*trigger = newTrigger()
			    .withIdentity("trigger3", "mygroup")
			    .withSchedule(dailyAtHourAndMinute(10, 42))
			    .forJob(job)
			    .build();
		
		//or
		trigger = newTrigger()
			    .withIdentity("trigger3", "mygroup")
			    .withSchedule(cronSchedule("0 42 10 * * ?"))
			    .forJob(job)
			    .build();
		
		//建立一个触发器，将在星期三上午10:42在TimeZone（系统默认值）之外触发：
		trigger = newTrigger()
			    .withIdentity("trigger3", "mygroup")
			    .withSchedule(weeklyOnDayAndHourAndMinute(DateBuilder.WEDNESDAY, 10, 42))
			    .forJob(job)
//			    .inTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
			    .build();
		
		//or 
		trigger = newTrigger()
			    .withIdentity("trigger3", "mygroup")
			    .withSchedule(cronSchedule("0 42 10 ? * WED"))
//			    .inTimeZone(TimeZone.getTimeZone("America/Los_Angeles"))
			    .forJob(job)
			    .build();
		
		//CronTrigger Misfire说明
		*//**
		 * MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY  -1 如果触发器未触发，请使用
		 * {@link Trigger＃MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY}指令。
		 * @返回更新的CronScheduleBuilder@请参阅触发器#MISFIRE_INSTRUCTION_IGNORE_MISFIRE_POLICY
		 * 
		 * MISFIRE_INSTRUCTION_DO_NOTHING 2  如果触发器未触发，请使用
		 * {@link CronTrigger＃MISFIRE_INSTRUCTION_DO_NOTHING}指令。@返回更新的CronScheduleBuilder
		 * @请参阅CronTrigger＃MISFIRE_INSTRUCTION_DO_NOTHING
		 * 
		 * MISFIRE_INSTRUCTION_FIRE_ONCE_NOW 1 如果触发器未触发，请使用
		 * {@link CronTrigger＃MISFIRE_INSTRUCTION_FIRE_ONCE_NOW}指令。
		 * @返回更新的CronScheduleBuilder@请参阅CronTrigger＃MISFIRE_INSTRUCTION_FIRE_ONCE_NOW
		 *//*
		trigger = newTrigger()
			    .withIdentity("trigger3", "mygroup")
			    .withSchedule(cronSchedule("0 0/2 8-17 * * ?")
			        .withMisfireHandlingInstructionFireAndProceed())
			    .forJob("myJob", "mygroup")
			    .build();*/
		
		//创建一个scheduler
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getContext().put("cronKey", "cronValue");
		//注册trigger并启动scheduler
        scheduler.scheduleJob(job,trigger);
        //局部监听
        scheduler.getListenerManager().addJobListener(new CloudJobListener(), jobGroupEquals("mygroup"));
        //全局监听
        scheduler.getListenerManager().addJobListener(new CloudJobListener(), EverythingMatcher.allJobs());
//        scheduler.getListenerManager().addJobListener(new CloudJobListener(), jobKeyEquals(jobKey("myJobName", "myJobGroup")));
        scheduler.start();
	}
}
