package tcd.ie.dublinbikes.scheduler.util;

import java.text.ParseException;
import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import tcd.ie.dublinbikes.scheduler.config.JobCronTriggerFactory;

public class SchedulerJobUtils {

	public static JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable, 
			ApplicationContext context, String jobName, String jobGroup){
		
	    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
	    factoryBean.setJobClass(jobClass);
	    factoryBean.setDurability(isDurable);
	    factoryBean.setApplicationContext(context);
	    factoryBean.setName(jobName);
	    factoryBean.setGroup(jobGroup);
        
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("myKey", "myValue");
        factoryBean.setJobDataMap(jobDataMap);
        
        factoryBean.afterPropertiesSet();
        
	    return factoryBean.getObject();
	}

	public static Trigger createCronTrigger(String triggerName, Date startTime, String cronExpression, int misFireInstruction){
		JobCronTriggerFactory factoryBean = new JobCronTriggerFactory();
	    factoryBean.setName(triggerName);
	    factoryBean.setStartTime(startTime);
	    factoryBean.setCronExpression(cronExpression);
	    factoryBean.setMisfireInstruction(misFireInstruction);
	    try {
	        factoryBean.afterPropertiesSet();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return factoryBean.getObject();
	}
	
	public static Trigger createSingleTrigger(String triggerName, Date startTime, int misFireInstruction){
		SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
	    factoryBean.setName(triggerName);
	    factoryBean.setStartTime(startTime);
	    factoryBean.setMisfireInstruction(misFireInstruction);
	    factoryBean.setRepeatCount(0);
	    factoryBean.afterPropertiesSet();
	    return factoryBean.getObject();
	}
	
}
