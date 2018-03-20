package tcd.ie.dublinbikes.scheduler.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import tcd.ie.dublinbikes.scheduler.service.SchedulerJobService;
import tcd.ie.dublinbikes.scheduler.util.SchedulerJobUtils;

/**
 * 
 * @author arun
 *
 */

@Service
public class SchedulerJobServiceImpl implements SchedulerJobService{

	private static final Logger logger = LoggerFactory.getLogger(SchedulerJobServiceImpl.class);
	
	@Autowired
	@Lazy
	SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ApplicationContext context;

	@Override
	public boolean scheduleOneTimeJob(String jobName, Class<? extends QuartzJobBean> jobClass, Date date) {
		logger.info("scheduling the job request");
		String jobKey = jobName;
		String groupKey = "groupKey";	
		String triggerKey = jobName;		

		JobDetail jobDetail = SchedulerJobUtils.createJob(jobClass, false, context, jobKey, groupKey);

		logger.info("invoking the trigger for key :"+jobKey + ", time :"+date);
		Trigger cronTriggerBean = SchedulerJobUtils.createSingleTrigger(triggerKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
		
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);
			logger.info("Jobkey :"+jobKey+ " and group :"+groupKey+ " scheduled successfully, time :"+dt);
			return true;
		} catch (SchedulerException e) {
			logger.error("exception while scheduling job with key :"+jobKey + " message :"+e.getMessage());
			e.printStackTrace();
		}

		return false;
	}
	
	@Override
	public boolean scheduleCronJob(String jobName, Class<? extends QuartzJobBean> jobClass, Date date, String cronExpression) {
		logger.info("Request received to scheduleJob");

		String jobKey = jobName;
		String groupKey = "groupKey";	
		String triggerKey = jobName;		

		JobDetail jobDetail = SchedulerJobUtils.createJob(jobClass, false, context, jobKey, groupKey);

		logger.info("creating trigger for key :"+jobKey + " at date :"+date);
		Trigger cronTriggerBean = SchedulerJobUtils.createCronTrigger(triggerKey, date, cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);
			logger.info("JobKey :"+jobKey+ " and group :"+groupKey+ " scheduled successfully for date :"+dt);
			return true;
		} catch (SchedulerException e) {
			logger.error("SchedulerException while scheduling job with key :"+jobKey + " message :"+e.getMessage());
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateOneTimeJob(String jobName, Date date) {
		logger.info("Request received for updating one time job.");

		String jobKey = jobName;

		logger.info("Parameters received for updating one time job : jobKey :"+jobKey + ", date: "+date);
		try {
			
			Trigger newTrigger = SchedulerJobUtils.createSingleTrigger(jobKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);
			logger.info("JobKey :"+jobKey+ " rescheduled successfully for date :"+dt);
			return true;
		} catch ( Exception e ) {
			logger.error("SchedulerException while updating one time job with key :"+jobKey + " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean updateCronJob(String jobName, Date date, String cronExpression) {
		logger.info("Request received for updating cron job.");

		String jobKey = jobName;

		logger.info("updating cron job : jobKey :"+jobKey + ", date: "+date);
		try {
			Trigger newTrigger = SchedulerJobUtils.createCronTrigger(jobKey, date, cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);

			Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobKey), newTrigger);
			logger.info("Trigger associated with jobKey :"+jobKey+ " rescheduled successfully for date :"+dt);
			return true;
		} catch ( Exception e ) {
			logger.error("exception while updating cron job with key :"+jobKey + " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean unScheduleJob(String jobName) {
		logger.info("Request received for Unscheduleding job.");

		String jobKey = jobName;

		TriggerKey tkey = new TriggerKey(jobKey);
		logger.info("unscheduling job : jobkey :"+jobKey);
		try {
			boolean status = schedulerFactoryBean.getScheduler().unscheduleJob(tkey);
			logger.info("Trigger associated with jobKey :"+jobKey+ " unscheduled with status :"+status);
			return status;
		} catch (SchedulerException e) {
			logger.error("SchedulerException while unscheduling job with key :"+jobKey + " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean deleteJob(String jobName) {
		logger.info("deleting job: " + jobName);

		String jobKey = jobName;
		String groupKey = "groupKey";

		JobKey jkey = new JobKey(jobKey, groupKey); 
		logger.info("Deleting job : jobKey :"+jobKey);

		try {
			boolean status = schedulerFactoryBean.getScheduler().deleteJob(jkey);
			logger.info("Job with jobKey :"+jobKey+ " deleted with status :"+status);
			return status;
		} catch (SchedulerException e) {
			logger.error("exception while deleting job with key :"+jobKey + " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean pauseJob(String jobName) {
		logger.info("pausing job: " + jobName);
		String jobKey = jobName;
		String groupKey = "groupKey";
		JobKey jkey = new JobKey(jobKey, groupKey); 
		logger.info("Parameters received for pausing job : jobKey :"+jobKey+ ", groupKey :"+groupKey);

		try {
			schedulerFactoryBean.getScheduler().pauseJob(jkey);
			logger.info("Job with jobKey :"+jobKey+ " paused succesfully.");
			return true;
		} catch (SchedulerException e) {
			logger.error("exception while pausing job with key :"+jobName + " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean resumeJob(String jobName) {
		logger.info("resuming job: " + jobName);

		String jobKey = jobName;
		String groupKey = "groupKey";

		JobKey jKey = new JobKey(jobKey, groupKey); 
		logger.info("Parameters received for resuming job : jobKey :"+jobKey);
		try {
			schedulerFactoryBean.getScheduler().resumeJob(jKey);
			logger.info("Job with jobKey :"+jobKey+ " resumed succesfully.");
			return true;
		} catch (SchedulerException e) {
			logger.error("exception while resuming job with key :"+jobKey+ " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean startJobNow(String jobName) {
		logger.info("starting job now: " + jobName); 

		String jobKey = jobName;
		String groupKey = "groupKey";

		JobKey jKey = new JobKey(jobKey, groupKey); 
		logger.info("Parameters received for starting job now : jobKey :"+jobKey);
		try {
			schedulerFactoryBean.getScheduler().triggerJob(jKey);
			logger.info("Job with jobKey :"+jobKey+ " started now succesfully.");
			return true;
		} catch (SchedulerException e) {
			logger.error("exception while starting job now with key :"+jobKey+ " message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public boolean isJobRunning(String jobName) {
		logger.info("check job is running:" + jobName);
		String jobKey = jobName;
		String groupKey = "groupKey";

		logger.info("Parameters received for checking job is running now : jobKey :"+jobKey);
		try {
			List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
			if(currentJobs!=null){
				for (JobExecutionContext jobCtx : currentJobs) {
					String jobNameDB = jobCtx.getJobDetail().getKey().getName();
					String groupNameDB = jobCtx.getJobDetail().getKey().getGroup();
					if (jobKey.equalsIgnoreCase(jobNameDB) && groupKey.equalsIgnoreCase(groupNameDB)) {
						return true;
					}
				}
			}
		} catch (SchedulerException e) {
			logger.error("exception while checking job with key :"+jobKey+ " is running. error message :"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getAllJobs() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			for (String groupName : scheduler.getJobGroupNames()) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();

					//get job's trigger
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					Date scheduleTime = triggers.get(0).getStartTime();
					Date nextFireTime = triggers.get(0).getNextFireTime();
					Date lastFiredTime = triggers.get(0).getPreviousFireTime();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("jobName", jobName);
					map.put("groupName", jobGroup);
					map.put("scheduleTime", scheduleTime);
					map.put("lastFiredTime", lastFiredTime);
					map.put("nextFireTime", nextFireTime);
					
					if(isJobRunning(jobName)){
						map.put("jobStatus", "RUNNING");
					}else{
						String jobState = getJobState(jobName);
						map.put("jobStatus", jobState);
					}

					list.add(map);
					logger.info("Job details:");
					logger.info("jobname:"+jobName + ", group:"+ groupName + ", scheduletime:"+scheduleTime);
				}

			}
		} catch (SchedulerException e) {
			logger.error("exception while fetching all jobs. error message :"+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean isJobWithNamePresent(String jobName) {
		try {
			String groupKey = "groupKey";
			JobKey jobKey = new JobKey(jobName, groupKey);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (scheduler.checkExists(jobKey)){
				return true;
			}
		} catch (SchedulerException e) {
			logger.error("exception while checking job with name and group exist:"+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public String getJobState(String jobName) {
		logger.info("Getting the job state: " + jobName);
		try {
			String groupKey = "groupKey";
			JobKey jobKey = new JobKey(jobName, groupKey);

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
			if(triggers != null && triggers.size() > 0){
				for (Trigger trigger : triggers) {
					TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

					if (TriggerState.PAUSED.equals(triggerState)) {
						return "PAUSED";
					}else if (TriggerState.BLOCKED.equals(triggerState)) {
						return "BLOCKED";
					}else if (TriggerState.COMPLETE.equals(triggerState)) {
						return "COMPLETE";
					}else if (TriggerState.ERROR.equals(triggerState)) {
						return "ERROR";
					}else if (TriggerState.NONE.equals(triggerState)) {
						return "NONE";
					}else if (TriggerState.NORMAL.equals(triggerState)) {
						return "SCHEDULED";
					}
				}
			}
		} catch (SchedulerException e) {
			logger.error("exception while checking job with name and group exist:"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean stopJob(String jobName) {
		logger.info("stopping the jobs: " + jobName);
		try{	
			String jobKey = jobName;
			String groupKey = "groupKey";

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jkey = new JobKey(jobKey, groupKey);

			return scheduler.interrupt(jkey);

		} catch (SchedulerException e) {
			logger.error("exception while stopping job. error message :"+e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}

