package tcd.ie.dublinbikes.console.service;

import java.util.List;

import tcd.ie.dublinbikes.console.entity.GraphAxis;

/**
 * 
 * @author arun
 *
 */
public abstract interface ITimeSeriesService {
	
	public List<GraphAxis> getHourlyPlotData(String data);

}
