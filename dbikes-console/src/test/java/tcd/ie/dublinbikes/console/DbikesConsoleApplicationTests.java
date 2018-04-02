package tcd.ie.dublinbikes.console;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import tcd.ie.dublinbikes.console.helper.ClientHelper;
import tcd.ie.dublinbikes.console.service.ITimeSeriesService;
import tcd.ie.dublinbikes.console.service.impl.TimeSeriesService;
import tcd.ie.dublinbikes.console.util.ComputeTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Service
public class DbikesConsoleApplicationTests {
	ComputeTime template = new ComputeTime();
	
	@Test
	public void contextLoads() {
		
	}
	
	ArrayList<String> time;
	
	@Test
	public void testhourTimediff() throws Exception {
		
		//List timeTest1 = new ArrayList();;
		 time = new ArrayList<>();
			// Day light saving fix
			Long currTime = System.currentTimeMillis() - (60 * 60* 1000);
			time.add(String.valueOf(currTime));
			Long prevTime = currTime - (60 * 60* 1000);	
			time.add(String.valueOf(prevTime));
		
		
		// timeTest1   = template1.hourTimeDiff();
		 
		 assertArrayEquals(time.toArray(), template.hourTimeDiff().toArray());
	}
	
	
	@Test
	public void testdailyTimeDiff() throws Exception {
		
		//List timeTest1 = new ArrayList();;
		 time = new ArrayList<>();
			Long currTime = System.currentTimeMillis();
			time.add(String.valueOf(currTime));
			Long prevTime = currTime - (24* 60 * 60* 1000);	
			time.add(String.valueOf(prevTime));
		
		
		// timeTest1   = template1.dailyTimeDiff();
		
		 assertArrayEquals(time.toArray(), template.dailyTimeDiff().toArray());
	    
	}
  
	@Test
	public void testweekTimeDiff() throws Exception {
		
		//List timeTest1 = new ArrayList();;
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(String.valueOf(currTime));
		Long prevTime = currTime - (7 * 24 * 60 * 60 * 1000);	
		time.add(String.valueOf(prevTime));
		
		
		// timeTest1   = template1.weekTimeDiff();
		 
		 assertArrayEquals(time.toArray(), template.weekTimeDiff().toArray());
	}
	
	
	@Test
	public void testmonthTimeDiff() throws Exception {
		time = new ArrayList<>();
		Long currTime = System.currentTimeMillis();
		time.add(String.valueOf(currTime));
		Long prevTime = currTime + (30 * 24 * 60 * 60 * 1000);
		time.add(String.valueOf(prevTime));

		
		//ComputeTime template4 = new ComputeTime();
		
		// timeTest1   = template1.monthTimeDiff();
		assertArrayEquals(time.toArray(), template.monthTimeDiff().toArray());  
	}
	
	
	@Autowired
	ClientHelper tsHelper;
	
	@Autowired
	ITimeSeriesService tsService;
	
	@Test
        public void testgetHourInfoById() throws Exception {
		Integer id = 23;
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.hourTimeDiff();
		String dataResp = tsHelper.getDataFromDb(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			TimeGraphResponse tgRes = tsService.getHourlyPlotData(String.valueOf(id), dataResp);
			
			ObjectMapper mapper = new ObjectMapper();
			assertEquals(dataResp, id);
		}
		
	}
	
	
	@Test
	public void testgetDailyInfoById() throws Exception {
		Integer id = 40;
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.dailyTimeDiff();
		String dataResp = tsHelper.getAverageDataFromDB(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			String predictData = tsHelper.getPredictDataFromS3();
			TimeGraphResponse tgRes = tsService.getDailyPlotData(String.valueOf(id), dataResp, predictData);
			
			ObjectMapper mapper = new ObjectMapper();
			assertEquals(dataResp, id);
	
		}
	}
	
	
	@Test
	public void testgetWeekInfoById() throws Exception {
		Integer id = 50;
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.weekTimeDiff();
		String dataResp = tsHelper.getAverageDataFromDB(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			
			String predictData = tsHelper.getPredictDataFromS3();
			TimeGraphResponse tgRes = tsService.getWeekPlotData(String.valueOf(id), dataResp, predictData);
			
			ObjectMapper mapper = new ObjectMapper();
			assertEquals(dataResp, id);
		}
	}
	
  @Test
  public void testgetMonthlyInfoById() throws Exception {
        Integer id = 100;
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.monthTimeDiff();
		String dataResp = tsHelper.getAverageDataFromDB(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			TimeGraphResponse tgRes = tsService.getHourlyPlotData(String.valueOf(id), dataResp);
			tgRes.setId(id.toString());
			
			ObjectMapper mapper = new ObjectMapper();
			assertEquals(dataResp, id);
		}

	}
  
 /**
  @Test
  public void testgetHourlyPlotData() throws Exception{	
		String id;
		String data = "Nassau Street";
		TimeGraphResponse tgr = new TimeGraphResponse();
		ComputeTime ct = new ComputeTime();
		ObjectMapper mapper = new ObjectMapper();
		
		DBikesStationWrapper dbikesList = new DBikesStationWrapper();
		List<GraphCoord> gAxes = new ArrayList<>();
		
			GraphCoord gA = null;
			List<DBikesStationData> dbikesData = dbikesList.getDbikes();
			for(DBikesStationData dbikes : dbikesData) {
				gA = new GraphCoord();
				gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setY(dbikes.getAvailable_bikes());
				gAxes.add(gA);
			}
			tgr.setMean(gAxes);

	    
	    assertEquals(tgr, data);
	}
  
  **/
	/**
	 @Test
	 public void testgetDailyPlotData() throws Exception {
		String id;
		String dbData;
		String predData;
		TimeGraphResponse tgr = new TimeGraphResponse();
		ComputeTime ct = new ComputeTime();
		ObjectMapper mapper = new ObjectMapper();
		
		List<GraphCoord> predXy = new ArrayList<>();
		List<DBikesPredictData> predList = new ArrayList<>();
		GraphCoord gA = null;
		
		DBikesAverageWrapper dbikesList = new DBikesAverageWrapper();
		List<GraphCoord> meanXy = new ArrayList<>();
		
	    
					
			List<DBikesAverageData> dbikesData = dbikesList.getDbikesData();
			for(DBikesAverageData dbikes : dbikesData) {
				gA = new GraphCoord();
				//gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setX("0");
				gA.setY(String.valueOf(dbikes.getAvg_available_bikes()));
				meanXy.add(gA);
			}
			
			tgr.setMean(meanXy);
		//getMeanPlotData(id, tgr);
		
			
			
			String tday = ct.getCurrentDay();
			for(DBikesPredictData pdata : predList) {
				
					gA = new GraphCoord();
					gA.setX(pdata.getDay() + ":" + pdata.getHour());
					gA.setY(pdata.getScore());
					predXy.add(gA);
			}
			tgr.setPredict(predXy);
			assertEquals(gA, tgr);
	}
    **/
	/**
	 @Test
	 public void testgetMeanPlotData() throws Exception {
       String dbData;
       TimeGraphResponse tgr;
		ObjectMapper mapper = new ObjectMapper();
		
		DBikesAverageWrapper dbikesList = new DBikesAverageWrapper();

		List<GraphCoord> meanXy = new ArrayList<>();
		GraphCoord gA = null;
				
			List<DBikesAverageData> dbikesData = dbikesList.getDbikesData();
			for(DBikesAverageData dbikes : dbikesData) {
				gA = new GraphCoord();
				//gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setX("0");
				gA.setY(String.valueOf(dbikes.getAvg_available_bikes()));
				meanXy.add(gA);
			}
	     assertEquals(dbikesData, dbikesList);
	}
	 **/
	/**
	 @Test
	 public void  testgetWeekPlotData() throws Exception {	
		String id;
		String dbData;
		String predData;
		TimeGraphResponse tgr = new TimeGraphResponse();
		//ComputeTime ct = new ComputeTime();
		ObjectMapper mapper = new ObjectMapper();
		
		List<GraphCoord> predXy = new ArrayList<>();
		List<DBikesPredictData> predList = null;
		GraphCoord gA = null;
		DBikesAverageWrapper dbikesList = new DBikesAverageWrapper();

		List<GraphCoord> meanXy = new ArrayList<>();
		
			List<DBikesAverageData> dbikesData = dbikesList.getDbikesData();
			for(DBikesAverageData dbikes : dbikesData) {
				gA = new GraphCoord();
				//gA.setX(ct.formatHourDate((dbikes.getLast_update())));
				gA.setX("0");
				gA.setY(String.valueOf(dbikes.getAvg_available_bikes()));
				meanXy.add(gA);
			}
			
			tgr.setMean(meanXy);
		 
		
		//getMeanPlotData(id, tgr);
	   
			
			Map<String, Double> daywiseScore = new LinkedHashMap<>();
			for(DBikesPredictData pdata : predList) {
					if(!daywiseScore.containsKey(pdata.getDay())) {
						daywiseScore.put(pdata.getDay(), Double.parseDouble(pdata.getScore()));
					} else {
						Double dscore = (double) Math.round((Double.parseDouble(pdata.getScore()) + 
								daywiseScore.get(pdata.getDay())));		
						daywiseScore.put(pdata.getDay(), dscore);
					}		
				
			}
			
			for(Map.Entry<String, Double> mentry : daywiseScore.entrySet()) {
				gA = new GraphCoord();
				gA.setX(mentry.getKey());
				gA.setY(String.valueOf(Math.round(mentry.getValue()/24)));
				predXy.add(gA);
			}
			tgr.setPredict(predXy);
		assertEquals(dbikesList, tgr);
	}
	 **/
	}
