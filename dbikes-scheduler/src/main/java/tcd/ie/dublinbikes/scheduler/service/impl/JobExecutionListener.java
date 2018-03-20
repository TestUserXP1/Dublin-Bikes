package tcd.ie.dublinbikes.scheduler.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author arun
 *
 */
@Component
public class JobExecutionListener implements JobListener{

	private static final Logger logger = LoggerFactory.getLogger(JobExecutionListener.class);
	@Override
	public String getName() {
		return "globalJob";
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		logger.info("jobs to be executed from joblistener");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		logger.info("jobs vetoed from joblistener");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		logger.info("jobs was executed from joblistener");
	}

}
