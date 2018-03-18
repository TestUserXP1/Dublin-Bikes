package tcd.ie.dublinbikes.console.util;

import java.util.ArrayList;

/**
 * 
 * @author arun
 *
 */
public class ComputeTime {
	
	ArrayList<Long> time;
	public ArrayList<Long> hourTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(currTime);
		Long prevTime = currTime - (60 * 60* 1000);	
		time.add(prevTime);
		return time;
	}
	
	public ArrayList<Long> dailyTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(currTime);
		Long prevTime = currTime - (24 * 60 * 60 * 1000);	
		time.add(prevTime);
		return time;
	}
	
	public ArrayList<Long> weekTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(currTime);
		Long prevTime = currTime - (7 * 24 * 60 * 60 * 1000);	
		time.add(prevTime);
		return time;
	}
	
	public ArrayList<Long> monthTimeDiff() {	
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(currTime);
		Long prevTime = currTime + (30 * 24 * 60 * 60 * 1000);
		time.add(prevTime);
		return time;
	}

}
