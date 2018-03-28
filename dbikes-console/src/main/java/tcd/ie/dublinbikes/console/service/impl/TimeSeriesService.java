package tcd.ie.dublinbikes.console.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tcd.ie.dublinbikes.console.entity.DBikesAverageData;
import tcd.ie.dublinbikes.console.entity.DBikesAverageWrapper;
import tcd.ie.dublinbikes.console.entity.DBikesPredictData;
import tcd.ie.dublinbikes.console.entity.DBikesStationData;
import tcd.ie.dublinbikes.console.entity.DBikesStationWrapper;
import tcd.ie.dublinbikes.console.entity.GraphCoord;
import tcd.ie.dublinbikes.console.entity.TimeGraphResponse;
import tcd.ie.dublinbikes.console.service.ITimeSeriesService;
import tcd.ie.dublinbikes.console.util.ComputeTime;

/**
 * 
 * @author arun
 *
 */

@Service
public class TimeSeriesService implements ITimeSeriesService {
		
	public TimeGraphResponse getHourlyPlotData(String id, String data) {	
		
		TimeGraphResponse tgr = new TimeGraphResponse();
		ComputeTime ct = new ComputeTime();
		ObjectMapper mapper = new ObjectMapper();
		
		DBikesStationWrapper dbikesList = new DBikesStationWrapper();
		List<GraphCoord> gAxes = new ArrayList<>();
		
	    try {
			dbikesList = mapper.readValue(data, DBikesStationWrapper.class);
			GraphCoord gA = null;
			List<DBikesStationData> dbikesData = dbikesList.getDbikes();
			for(DBikesStationData dbikes : dbikesData) {
				gA = new GraphCoord();
				gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setY(dbikes.getAvailable_bikes());
				gAxes.add(gA);
			}
			tgr.setMean(gAxes);
			tgr.setId(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return tgr;
	}
	
	public TimeGraphResponse getDailyPlotData(String id, String dbData, String predData) {
		
		TimeGraphResponse tgr = new TimeGraphResponse();
		ComputeTime ct = new ComputeTime();
		ObjectMapper mapper = new ObjectMapper();
		
		List<GraphCoord> predXy = new ArrayList<>();
		List<DBikesPredictData> predList = new ArrayList<>();
		GraphCoord gA = null;
		
		DBikesAverageWrapper dbikesList = new DBikesAverageWrapper();
		List<GraphCoord> meanXy = new ArrayList<>();
		
	    try {
			dbikesList = mapper.readValue(dbData, DBikesAverageWrapper.class);		
			List<DBikesAverageData> dbikesData = dbikesList.getDbikesData();
			for(DBikesAverageData dbikes : dbikesData) {
				gA = new GraphCoord();
				//gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setX("0");
				gA.setY(String.valueOf(dbikes.getAvg_available_bikes()));
				meanXy.add(gA);
			}
			tgr.setId(id);
			tgr.setMean(meanXy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//getMeanPlotData(id, tgr);
		
	    try {
			predList = mapper.readValue(predData, new TypeReference<List<DBikesPredictData>>() { });
			
			String tday = ct.getCurrentDay();
			for(DBikesPredictData pdata : predList) {
				if(pdata.getNumber().equals(id) && pdata.getDay().equals(tday)) {
					gA = new GraphCoord();
					gA.setX(pdata.getDay() + ":" + pdata.getHour());
					gA.setY(pdata.getScore());
					predXy.add(gA);
				}
			}
			tgr.setPredict(predXy);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return tgr;
	}

	
	
	public void getMeanPlotData(String dbData, TimeGraphResponse tgr) {

		ObjectMapper mapper = new ObjectMapper();
		
		DBikesAverageWrapper dbikesList = new DBikesAverageWrapper();

		List<GraphCoord> meanXy = new ArrayList<>();
		GraphCoord gA = null;
		
	    try {
			dbikesList = mapper.readValue(dbData, DBikesAverageWrapper.class);		
			List<DBikesAverageData> dbikesData = dbikesList.getDbikesData();
			for(DBikesAverageData dbikes : dbikesData) {
				gA = new GraphCoord();
				//gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setX("0");
				gA.setY(String.valueOf(dbikes.getAvg_available_bikes()));
				meanXy.add(gA);
			}
			tgr.setMean(meanXy);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	}
	
	
	public TimeGraphResponse getWeekPlotData(String id, String dbData, String predData) {	
		
		TimeGraphResponse tgr = new TimeGraphResponse();
		//ComputeTime ct = new ComputeTime();
		ObjectMapper mapper = new ObjectMapper();
		
		List<GraphCoord> predXy = new ArrayList<>();
		List<DBikesPredictData> predList = null;
		GraphCoord gA = null;
		DBikesAverageWrapper dbikesList = new DBikesAverageWrapper();

		List<GraphCoord> meanXy = new ArrayList<>();
		
	    try {
			dbikesList = mapper.readValue(dbData, DBikesAverageWrapper.class);		
			List<DBikesAverageData> dbikesData = dbikesList.getDbikesData();
			for(DBikesAverageData dbikes : dbikesData) {
				gA = new GraphCoord();
				//gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setX("0");
				gA.setY(String.valueOf(dbikes.getAvg_available_bikes()));
				meanXy.add(gA);
			}
			tgr.setId(id);
			tgr.setMean(meanXy);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//getMeanPlotData(id, tgr);
	    try {
			predList = mapper.readValue(predData, new TypeReference<List<DBikesPredictData>>() { });
			Map<String, Double> daywiseScore = new LinkedHashMap<>();
			for(DBikesPredictData pdata : predList) {
				if(pdata.getNumber().equals(id)) {
					if(!daywiseScore.containsKey(pdata.getDay())) {
						daywiseScore.put(pdata.getDay(), Double.parseDouble(pdata.getScore()));
					} else {
						Double dscore = (double) Math.round((Double.parseDouble(pdata.getScore()) + 
								daywiseScore.get(pdata.getDay())));		
						daywiseScore.put(pdata.getDay(), dscore);
					}		
				}
			}
			
			for(Map.Entry<String, Double> mentry : daywiseScore.entrySet()) {
				gA = new GraphCoord();
				gA.setX(mentry.getKey());
				gA.setY(String.valueOf(Math.round(mentry.getValue()/24)));
				predXy.add(gA);
			}
			tgr.setPredict(predXy);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    return tgr;
	}
	
}
