package tcd.ie.dublinbikes.console.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author arun
 *
 */
public class AppPropertyLoader {

	public static Properties loadProperties() {
		InputStream input = null;
		Properties props = new Properties();
		try {
			String filename = "application.properties";
			input = AppPropertyLoader.class.getClassLoader().getResourceAsStream(filename);
			if (input == null) {
				System.out.println("Sorry, unable to find " + filename);
				return null;
			}
			props.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return props;
	}
	
	public String getProperty(String key) {	
		return loadProperties().getProperty(key);
	}
}
