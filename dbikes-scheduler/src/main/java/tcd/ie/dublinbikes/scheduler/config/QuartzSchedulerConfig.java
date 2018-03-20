package tcd.ie.dublinbikes.scheduler.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import tcd.ie.dublinbikes.scheduler.service.impl.JobExecutionListener;
import tcd.ie.dublinbikes.scheduler.service.impl.TriggerExecutionListener;

/**
 * 
 * @author arun
 *
 */

@Configuration
public class QuartzSchedulerConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerConfig.class);
 
    @Autowired
    DataSource dataSource;
 
    @Autowired
    private ApplicationContext applicationContext;
     
    @Autowired
    private TriggerExecutionListener triggerExecListener;

    @Autowired
    private JobExecutionListener jobExecListener;
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
 
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(getQuartzProperties());
       
        factory.setGlobalTriggerListeners(triggerExecListener);
        factory.setGlobalJobListeners(jobExecListener);
        
        ApplicationSpringBeanJobFactory jobFactory = new ApplicationSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        factory.setJobFactory(jobFactory);
        
        return factory;
    }

    @Bean
    public Properties getQuartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        if(logger.isDebugEnabled()) {	
        		logger.debug("Loading the quartz properties file");
        }
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}