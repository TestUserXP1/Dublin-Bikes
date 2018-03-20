package tcd.ie.dublinbikes.scheduler.job;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.quartz.InterruptableJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.UnableToInterruptJobException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import tcd.ie.dublinbikes.scheduler.service.SchedulerJobService;

/**
 * 
 * @author arun
 *
 */

public class CronQuartzJob extends QuartzJobBean implements InterruptableJob{
	
	private static final Logger logger = LoggerFactory.getLogger(CronQuartzJob.class);
	
	private volatile boolean toStopFlag = true;
	
	@Autowired
	SchedulerJobService jobService;
	
	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		
		logger.info("Executing cron job with key: " + key.getName() + ", group: " + key.getGroup() + ", threadname: " + Thread.currentThread().getName() 
				+ ",execution time :"+new Date());
		
		logger.info("Getting all jobs: " + jobService.getAllJobs());
		List<Map<String, Object>> list = jobService.getAllJobs();
		logger.info("list of all jobs: " + list);
		JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
		String value = dataMap.getString("myKey");
		logger.info("Value: " + value + " threadname: " + Thread.currentThread().getName() + " is stopped" );
	}

	@Override
	public void interrupt() throws UnableToInterruptJobException {
		logger.info("Job Interrupted and it is stopping");
		setToStopFlag(false);
	}

	public boolean isToStopFlag() {
		return toStopFlag;
	}

	public void setToStopFlag(boolean toStopFlag) {
		this.toStopFlag = toStopFlag;
	}

}