package tcd.ie.dublinbikes.scheduler.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 
 * @author arun
 *
 */

public interface SchedulerJobService {
	
	public abstract boolean scheduleOneTimeJob(String jobName, Class<? extends QuartzJobBean> jobClass, Date date);
	public abstract boolean scheduleCronJob(String jobName, Class<? extends QuartzJobBean> jobClass, Date date, String cronExpression);
	public abstract boolean updateOneTimeJob(String jobName, Date date);
	public abstract boolean updateCronJob(String jobName, Date date, String cronExpression);	
	public abstract boolean unScheduleJob(String jobName);
	public abstract boolean deleteJob(String jobName);
	public abstract boolean pauseJob(String jobName);
	public abstract boolean resumeJob(String jobName);
	public abstract boolean startJobNow(String jobName);
	public abstract boolean isJobRunning(String jobName);
	public abstract List<Map<String, Object>> getAllJobs();
	public abstract boolean isJobWithNamePresent(String jobName);
	public abstract String getJobState(String jobName);
	public abstract boolean stopJob(String jobName);
}
