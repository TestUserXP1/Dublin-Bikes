package tcd.ie.dublinbikes.console.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tcd.ie.dublinbikes.console.entity.GraphAxis;
import tcd.ie.dublinbikes.console.entity.TimeGraphResponse;
import tcd.ie.dublinbikes.console.helper.TimeSeriesHelper;
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
	TimeSeriesHelper tsHelper;
	
	@Autowired
	ITimeSeriesService tsService;
	
	@RequestMapping(value={"/hour/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getHourInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<Long> time = ct.hourTimeDiff();
		String dataResp = tsHelper.getDataFromDb(id.toString(), time);
		
		System.out.println(dataResp);
		List<GraphAxis> graphAxis = tsService.getHourlyPlotData(dataResp);
		TimeGraphResponse tgRes = new TimeGraphResponse();
		tgRes.setId(id.toString());
		tgRes.setGraphAxis(graphAxis);
		
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = mapper.writeValueAsString(tgRes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@RequestMapping(value={"/daily/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getDailyInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<Long> time = ct.dailyTimeDiff();
		String dataResp = tsHelper.getDataFromDb(id.toString(), time);
		
		System.out.println(dataResp);
		List<GraphAxis> graphAxis = tsService.getHourlyPlotData(dataResp);
		TimeGraphResponse tgRes = new TimeGraphResponse();
		tgRes.setId(id.toString());
		tgRes.setGraphAxis(graphAxis);
		
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = mapper.writeValueAsString(tgRes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return response;
	}
  
	@RequestMapping(value={"/week/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getWeekInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<Long> time = ct.weekTimeDiff();
		String dataResp = tsHelper.getDataFromDb(id.toString(), time);
		
		System.out.println(dataResp);
		List<GraphAxis> graphAxis = tsService.getHourlyPlotData(dataResp);
		TimeGraphResponse tgRes = new TimeGraphResponse();
		tgRes.setId(id.toString());
		tgRes.setGraphAxis(graphAxis);
		
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = mapper.writeValueAsString(tgRes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@RequestMapping(value={"/month/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getMonthlyInfoById(@PathVariable Integer id) {

		ComputeTime ct = new ComputeTime();
		ArrayList<Long> time = ct.monthTimeDiff();
		String dataResp = tsHelper.getDataFromDb(id.toString(), time);
		
		System.out.println(dataResp);
		List<GraphAxis> graphAxis = tsService.getHourlyPlotData(dataResp);
		TimeGraphResponse tgRes = new TimeGraphResponse();
		tgRes.setId(id.toString());
		tgRes.setGraphAxis(graphAxis);
		
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = mapper.writeValueAsString(tgRes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return response;
	}

}
