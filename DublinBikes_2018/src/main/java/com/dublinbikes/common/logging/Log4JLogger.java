package com.dublinbikes.common.logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.dublinbikes.common.constants.DataServiceConstants;

/**
 * 
 * @author arun
 *
 */
public final class Log4JLogger implements Log{
	
	private static final String className = Log4JLogger.class.getName();
	
	private Logger logger = null;

	static boolean formatLogEnabled = false;
	
	private static final String file_separator = System.getProperty("file.separator");

	public static final String _logPropFileSuffix = "Log4j.properties";
	
	private static Set<String> configuredLogPropFilePrefixes = new HashSet<String>();
	
	static {
		try {
			// turn off the stack trace
			System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace",
					"false");
			String configPath = System.getProperty(DataServiceConstants.SERVICES_CONFIGURATION_PATH);
			if (null == configPath) {
				configPath = System.getProperty(DataServiceConstants.CATALINA_BASE) + "/conf/";
			}
			System.setProperty(DataServiceConstants.SERVICES_LOG_CONFIGURATION_PATH, configPath);
		} catch (Exception e) {
			System.out.println("SEVERE: Unable to initialize logger.");
			e.printStackTrace();
		}
	}
	
	
	public static void initLogProps(final String logFilePropertyPrefix) {
		
		final String logFileName = file_separator + logFilePropertyPrefix + _logPropFileSuffix;
		
        synchronized (Log4JLogger.class) {
            Properties logProps = new Properties();
            //BasicConfigurator.configure();
            try {
                System.out.println("Load log properties file " + logFileName);
                String propertyFile = logFileName;
                if (null != System.getProperty(DataServiceConstants.SERVICES_LOG_CONFIGURATION_PATH)) {
                    propertyFile = System.getProperty(DataServiceConstants.SERVICES_LOG_CONFIGURATION_PATH)
                            + logFileName;
                } else {
                    propertyFile = System.getProperty(DataServiceConstants.CATALINA_BASE)
                            + "/conf/" + logFileName;
                }
                
                try( FileInputStream fis=new FileInputStream(propertyFile)) {

                    System.out.println("Loading " + propertyFile);
                    logProps.load(fis);
                    System.out.println("Loaded " + propertyFile);
                  
                    PropertyConfigurator.configureAndWatch(propertyFile,
                            Integer.parseInt(logProps.getProperty("log4j_update_watch_interval")));
                    
            		configuredLogPropFilePrefixes.add(logFilePropertyPrefix);
            		
                } catch (NumberFormatException nex) {
                 
                    System.out.println("Log4J update watch interval is set to default value: 1 minute.");
                    PropertyConfigurator.configureAndWatch(propertyFile, 60000);
                    
                }
            } catch (FileNotFoundException fex) {
                System.out.println("Unable to load  " + logFileName + " file");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	
	public Log4JLogger() {
	}
	
	public Log4JLogger(String name) {
		this.logger = Logger.getLogger(name);
	}

	public Log4JLogger(Logger logger) {
		this.logger = logger;
	}
	
	private static String format(String message) {
		if (formatLogEnabled) {
			return new NameValuePairLogMessage(message).toString();
		} else {
			return message;
		}
	}

	@Override
	public void debug(Object message) {
		debug(message, null);
		
	}

	@Override
	public void debug(Object message, Throwable t) {
		logger.log(className, Level.DEBUG , message, t);
		
	}

	@Override
	public void error(Object message) {
		error(message, null);
		
	}

	@Override
	public void error(Object message, Throwable t) {
		logger.log(className, Level.ERROR , message, t);
		
	}

	@Override
	public void fatal(Object message) {
		fatal(message, null);
		
	}

	@Override
	public void fatal(Object message, Throwable t) {
		logger.log(className, Level.FATAL , message, t);
		
	}

	@Override
	public void info(Object message) {
		info(message, null);
		
	}

	@Override
	public void info(Object message, Throwable t) {
		logger.log(className, Level.INFO , message, t);
		
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isEnabledFor(Level.ERROR);
	}

	@Override
	public boolean isFatalEnabled() {
		return logger.isEnabledFor(Level.FATAL);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isEnabledFor(Level.WARN);
	}

	@Override
	public void trace(Object message) {
		trace(message, null);	
	}

	@Override
	public void trace(Object message, Throwable t) {
		logger.log(className, Level.TRACE , message, t);
		
	}

	@Override
	public void warn(Object message) {
		warn(message, null);
		
	}

	@Override
	public void warn(Object message, Throwable t) {
		logger.log(className, Level.WARN, message, t);
		
	}
	
	public static final Log4JLogger getLogger(String name) {
		return new Log4JLogger(name);
	}

	public static final Log4JLogger getLogger(Class c) {
		return new Log4JLogger(c.getName());
	}

}
