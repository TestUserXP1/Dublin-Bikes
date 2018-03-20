package tcd.ie.dublinbikes.scheduler.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tcd.ie.dublinbikes.scheduler.entity.JobServerResponse;
import tcd.ie.dublinbikes.scheduler.job.CronQuartzJob;
import tcd.ie.dublinbikes.scheduler.job.SimpleQuartzJob;
import tcd.ie.dublinbikes.scheduler.service.SchedulerJobService;
import tcd.ie.dublinbikes.scheduler.util.JobServerResponseCode;

/**
 * 
 * @author arun
 *
 */

@RestController
@RequestMapping("/scheduler/")
public class SchedulerJobController {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerJobController.class);
	
	@Autowired
	@Lazy
	SchedulerJobService jobService;

	@RequestMapping("schedule")	
	public JobServerResponse scheduleByJobName(@RequestParam("jobName") String jobName, 
			@RequestParam("jobScheduleTime") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") Date jobScheduleTime, 
			@RequestParam("cronExpression") String cronExpression){
		
		logger.info("Scheduling the Jobs" + jobName);

		if(jobName == null || jobName.trim().equals("")){
			return getJobServerResponse(JobServerResponseCode.JOB_NAME_NOT_PRESENT, false);
		}

		if(!jobService.isJobWithNamePresent(jobName)){
			if(cronExpression == null || cronExpression.trim().equals("")){
				boolean status = jobService.scheduleOneTimeJob(jobName, SimpleQuartzJob.class, jobScheduleTime);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, jobService.getAllJobs());
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}
				
			}else{
				boolean status = jobService.scheduleCronJob(jobName, CronQuartzJob.class, jobScheduleTime, cronExpression);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, jobService.getAllJobs());
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}				
			}
		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_WITH_SAME_NAME_EXIST, false);
		}
	}

	@RequestMapping("unschedule")
	public void unscheduleByJobName(@RequestParam("jobName") String jobName) {
		logger.info("unschedule the job: " + jobName);
		jobService.unScheduleJob(jobName);
	}

	@RequestMapping("delete")
	public JobServerResponse deleteByJobName(@RequestParam("jobName") String jobName) {
		logger.info("deleting the job: " + jobName);

		if(jobService.isJobWithNamePresent(jobName)){
			boolean isJobRunning = jobService.isJobRunning(jobName);

			if(!isJobRunning){
				boolean status = jobService.deleteJob(jobName);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, true);
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}
			}else{
				return getJobServerResponse(JobServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
			}
		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_DOESNT_EXIST, false);
		}
	}

	@RequestMapping("pause")
	public JobServerResponse pauseByJobName(@RequestParam("jobName") String jobName) {
		logger.info("pausing the job: " + jobName);

		if(jobService.isJobWithNamePresent(jobName)){

			boolean isJobRunning = jobService.isJobRunning(jobName);

			if(!isJobRunning){
				boolean status = jobService.pauseJob(jobName);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, true);
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}			
			}else{
				return getJobServerResponse(JobServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
			}

		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_DOESNT_EXIST, false);
		}		
	}

	@RequestMapping("resume")
	public JobServerResponse resumeByJobName(@RequestParam("jobName") String jobName) {
		logger.info("resume the job: " + jobName);

		if(jobService.isJobWithNamePresent(jobName)){
			String jobState = jobService.getJobState(jobName);

			if(jobState.equals("PAUSED")){
				logger.info("The current state of the job is paused, resuming the following job: "+ jobName);
				boolean status = jobService.resumeJob(jobName);

				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, true);
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}
			}else{
				return getJobServerResponse(JobServerResponseCode.JOB_NOT_IN_PAUSED_STATE, false);
			}

		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_DOESNT_EXIST, false);
		}
	}

	@RequestMapping("update")
	public JobServerResponse updateJobByJobName(@RequestParam("jobName") String jobName, 
			@RequestParam("jobScheduleTime") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") Date jobScheduleTime, 
			@RequestParam("cronExpression") String cronExpression){
		logger.info("update the existing job: " + jobName);

		if(jobName == null || jobName.trim().equals("")){
			return getJobServerResponse(JobServerResponseCode.JOB_NAME_NOT_PRESENT, false);
		}

		if(jobService.isJobWithNamePresent(jobName)){
			
			if(cronExpression == null || cronExpression.trim().equals("")){
				boolean status = jobService.updateOneTimeJob(jobName, jobScheduleTime);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, jobService.getAllJobs());
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}
				
			}else{
				boolean status = jobService.updateCronJob(jobName, jobScheduleTime, cronExpression);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, jobService.getAllJobs());
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}				
			}
			
			
		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_DOESNT_EXIST, false);
		}
	}

	@RequestMapping("jobs")
	public JobServerResponse getAllJobs(){
		logger.info("list of all the job");

		List<Map<String, Object>> list = jobService.getAllJobs();
		return getJobServerResponse(JobServerResponseCode.SUCCESS, list);
	}

	@RequestMapping("checkJobName")
	public JobServerResponse checkJobName(@RequestParam("jobName") String jobName){
		logger.info("check for the job: " + jobName);

		if(jobName == null || jobName.trim().equals("")){
			return getJobServerResponse(JobServerResponseCode.JOB_NAME_NOT_PRESENT, false);
		}
		
		boolean status = jobService.isJobWithNamePresent(jobName);
		return getJobServerResponse(JobServerResponseCode.SUCCESS, status);
	}

	@RequestMapping("isJobRunning")
	public JobServerResponse checkForJobRunningStatus(@RequestParam("jobName") String jobName) {
		logger.info("check for the job running status: " + jobName);
		boolean status = jobService.isJobRunning(jobName);
		return getJobServerResponse(JobServerResponseCode.SUCCESS, status);
	}

	@RequestMapping("jobState")
	public JobServerResponse getJobStateByJobName(@RequestParam("jobName") String jobName) {
		logger.info("check for the job state: " + jobName);
		String jobState = jobService.getJobState(jobName);
		return getJobServerResponse(JobServerResponseCode.SUCCESS, jobState);
	}

	@RequestMapping("stop")
	public JobServerResponse stopJobByJobName(@RequestParam("jobName") String jobName) {
		logger.info("stop the job: " + jobName);
		if(jobService.isJobWithNamePresent(jobName)){
			if(jobService.isJobRunning(jobName)){
				boolean status = jobService.stopJob(jobName);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, true);
				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}
			}else{
				return getJobServerResponse(JobServerResponseCode.JOB_NOT_IN_RUNNING_STATE, false);
			}
		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_DOESNT_EXIST, false);
		}
	}

	@RequestMapping("start")
	public JobServerResponse startJobNow(@RequestParam("jobName") String jobName) {
		logger.info("start the job now: " + jobName);
		if(jobService.isJobWithNamePresent(jobName)){
			if(!jobService.isJobRunning(jobName)){
				boolean status = jobService.startJobNow(jobName);
				if(status){
					return getJobServerResponse(JobServerResponseCode.SUCCESS, true);

				}else{
					return getJobServerResponse(JobServerResponseCode.ERROR, false);
				}
			}else{
				return getJobServerResponse(JobServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
			}

		}else{
			return getJobServerResponse(JobServerResponseCode.JOB_DOESNT_EXIST, false);
		}
	}

	public JobServerResponse getJobServerResponse(int responseCode, Object data){
		JobServerResponse serverResponse = new JobServerResponse();
		serverResponse.setStatusCode(responseCode);
		serverResponse.setData(data);
		return serverResponse; 
	}
}
