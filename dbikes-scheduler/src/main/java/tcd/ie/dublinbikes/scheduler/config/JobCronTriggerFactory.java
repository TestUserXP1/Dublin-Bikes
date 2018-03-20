package tcd.ie.dublinbikes.scheduler.config;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;

/**
 * 
 * @author arun
 *
 */

public class JobCronTriggerFactory extends CronTriggerFactoryBean {
	
	private static final Logger logger = LoggerFactory.getLogger(JobCronTriggerFactory.class);
	
	public static final String JOB_DETAIL_KEY = "jobDetail";
	
    @Override
    public void afterPropertiesSet() throws ParseException{
        super.afterPropertiesSet();
        logger.info("Removing the job details: " + JOB_DETAIL_KEY);
        getJobDataMap().remove(JOB_DETAIL_KEY);
    }
}