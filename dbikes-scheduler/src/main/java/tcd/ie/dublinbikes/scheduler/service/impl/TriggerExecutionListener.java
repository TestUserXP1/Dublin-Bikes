package tcd.ie.dublinbikes.scheduler.service.impl;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.quartz.TriggerListener;
import org.springframework.stereotype.Component;

/**
 * 
 * @author arun
 *
 */
@Component
public class TriggerExecutionListener implements TriggerListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerJobServiceImpl.class);

    @Override
    public String getName() {
        return "globalTrigger";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
    		logger.info("trigger is fired");
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
    		logger.info("veto job is triggered in triggerlistener");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
    		logger.info("trigger is misfired");
        String jobName = trigger.getJobKey().getName();
        logger.info("jobname: " + jobName + " is misfired");
        
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context, CompletedExecutionInstruction triggerInstructionCode) {    
    		logger.info("trigger is completed");
    }
}
