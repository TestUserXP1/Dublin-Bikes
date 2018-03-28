package tcd.ie.dublinbikes.console.service;

import tcd.ie.dublinbikes.console.entity.TimeGraphResponse;

/**
 * 
 * @author arun
 *
 */
public abstract interface ITimeSeriesService {
	
	public TimeGraphResponse getHourlyPlotData(String id, String data);
	
	public TimeGraphResponse getDailyPlotData(String id, String dbData, String predData);
	
	public TimeGraphResponse getWeekPlotData(String id, String dbData, String predData);

}
