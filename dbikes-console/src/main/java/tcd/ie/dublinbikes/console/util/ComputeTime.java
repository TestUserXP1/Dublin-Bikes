package tcd.ie.dublinbikes.console.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author arun
 *
 */
public class ComputeTime {

	
	private static final SimpleDateFormat hr = new SimpleDateFormat("HH:mm");
	private static final SimpleDateFormat actual = new SimpleDateFormat("YYYY-MM-dd HH:mm");
	ArrayList<String> time;
	public ArrayList<String> hourTimeDiff() {	
		time = new ArrayList<>();
		// Day light saving fix
		Long currTime = System.currentTimeMillis() - (60 * 60* 1000);
		time.add(String.valueOf(currTime));
		Long prevTime = currTime - (60 * 60* 1000);	
		time.add(String.valueOf(prevTime));
		return time;
	}
	
	public ArrayList<String> dailyTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(String.valueOf(currTime));
		Long prevTime = currTime - (24 * 60 * 60 * 1000);	
		time.add(String.valueOf(prevTime));
		return time;
	}
	
	public ArrayList<String> weekTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(String.valueOf(currTime));
		Long prevTime = currTime - (7 * 24 * 60 * 60 * 1000);	
		time.add(String.valueOf(prevTime));
		return time;
	}
	
	public ArrayList<String> monthTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(String.valueOf(currTime));
		Long prevTime = currTime + (30 * 24 * 60 * 60 * 1000);
		time.add(String.valueOf(prevTime));
		return time;
	}
	
	public String formatHourDate(String date) {
		Date newDate = null;
		try {
			newDate = actual.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hr.format(newDate);
	}
	
	public String getCurrentDay() {
		String[] wdays = new String[]{"Sun", "Mon", "Tue", "Wed", "Thur", "Fri"};
		Calendar cal = Calendar.getInstance();
	    cal.setTime(new Date(System.currentTimeMillis()));
	    int week = cal.get(Calendar.DAY_OF_WEEK);
	    return wdays[week-1];
	}

}
