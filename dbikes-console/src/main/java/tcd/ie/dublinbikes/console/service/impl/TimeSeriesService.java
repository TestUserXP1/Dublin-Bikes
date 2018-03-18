package tcd.ie.dublinbikes.console.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tcd.ie.dublinbikes.console.entity.DBikesStation;
import tcd.ie.dublinbikes.console.entity.GraphAxis;
import tcd.ie.dublinbikes.console.service.ITimeSeriesService;

/**
 * 
 * @author arun
 *
 */

@Service
public class TimeSeriesService implements ITimeSeriesService {
	
	public List<GraphAxis> getHourlyPlotData(String data) {
		
		ObjectMapper mapper = new ObjectMapper();
		List<DBikesStation> dbikesList = null;
		List<GraphAxis> gAxes = new ArrayList<>();
		
	    try {
			dbikesList = mapper.readValue(data, new TypeReference<List<DBikesStation>>() { });
			GraphAxis gA = null;
			for(DBikesStation dbikes : dbikesList) {
				gA = new GraphAxis();
				gA.setxAxis(String.valueOf(dbikes.getLast_update()));
				gA.setyAxis(dbikes.getAvailable_bikes());
				gAxes.add(gA);
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return gAxes;
	}
	
}
