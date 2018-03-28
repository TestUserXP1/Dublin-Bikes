package tcd.ie.dublinbikes.console.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tcd.ie.dublinbikes.console.entity.TimeGraphResponse;
import tcd.ie.dublinbikes.console.helper.ClientHelper;
import tcd.ie.dublinbikes.console.service.ITimeSeriesService;
import tcd.ie.dublinbikes.console.util.ComputeTime;


/**
 * 
 * @author arun
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping({"/ts"})
public class TimeSeriesController {
	
	@Autowired
	ClientHelper tsHelper;
	
	@Autowired
	ITimeSeriesService tsService;
	
	@RequestMapping(value={"/hour/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getHourInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.hourTimeDiff();
		String dataResp = tsHelper.getDataFromDb(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			TimeGraphResponse tgRes = tsService.getHourlyPlotData(String.valueOf(id), dataResp);
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				response = mapper.writeValueAsString(tgRes);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}	
		}
		return response;
	}
	
	@RequestMapping(value={"/daily/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getDailyInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.dailyTimeDiff();
		String dataResp = tsHelper.getAverageDataFromDB(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			
			String predictData = tsHelper.getPredictDataFromS3();
			TimeGraphResponse tgRes = tsService.getDailyPlotData(String.valueOf(id), dataResp, predictData);
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				response = mapper.writeValueAsString(tgRes);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
  
	@RequestMapping(value={"/week/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getWeekInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.weekTimeDiff();
		String dataResp = tsHelper.getAverageDataFromDB(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			
			String predictData = tsHelper.getPredictDataFromS3();
			TimeGraphResponse tgRes = tsService.getWeekPlotData(String.valueOf(id), dataResp, predictData);
			
			ObjectMapper mapper = new ObjectMapper();
			
			try {
				response = mapper.writeValueAsString(tgRes);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return response;
	}
	
	@RequestMapping(value={"/month/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getMonthlyInfoById(@PathVariable Integer id) {

		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.monthTimeDiff();
		String dataResp = tsHelper.getAverageDataFromDB(id.toString(), time);
		String response = null;
		if(dataResp != null) {
			System.out.println(dataResp);
			TimeGraphResponse tgRes = tsService.getHourlyPlotData(String.valueOf(id), dataResp);
			tgRes.setId(id.toString());
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				response = mapper.writeValueAsString(tgRes);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

}
