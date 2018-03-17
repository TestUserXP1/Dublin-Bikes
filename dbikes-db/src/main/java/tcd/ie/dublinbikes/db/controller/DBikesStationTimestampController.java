package tcd.ie.dublinbikes.db.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import tcd.ie.dublinbikes.db.entity.DBikesStationTimestamp;
import tcd.ie.dublinbikes.db.services.IDBikesStationTimestampService;

/**
 * 
 * @author arun
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping({"/dbikes"})
public class DBikesStationTimestampController {
	
private static final Logger LOGGER = LoggerFactory.getLogger(DBikesStationTimestampController.class);
	
	@Autowired
	private IDBikesStationTimestampService bikesTSService;
	  
	@Bean
	public Gson gson() {
		return new Gson();
	}
	
	  
	@RequestMapping(value={"/list"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String listDublinBikes() {
		String response = gson().toJson(this.bikesTSService
		      .listAll(), new TypeToken<List<DBikesStationTimestamp>>() {}.getType());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Content: " + response);
		}
		return response;
	}
  
	@RequestMapping(value={"/view/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getDublinBikesById(@PathVariable Integer id) {
		String response = gson().toJson(this.bikesTSService.findBikesById(id),
				new TypeToken<List<DBikesStationTimestamp>>() {}.getType());
		if (LOGGER.isDebugEnabled()) {
		  LOGGER.debug("Content: " + response);
		}
		return response;
	}
	
	@RequestMapping(value={"/view/{id}/{stime}/{etime}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getDublinBikesByIdTime(@PathVariable Integer id, @PathVariable String stime, @PathVariable String etime) {
		String response = null;
		try {
			response = gson().toJson(this.bikesTSService.findBikesByIdTime(id, Long.parseLong(stime), Long.parseLong(etime)),
					new TypeToken<List<DBikesStationTimestamp>>() {}.getType());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
		  LOGGER.debug("Content: " + response);
		}
		return response;
	}
	
	@RequestMapping(value={"/view/{stime}/{etime}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getDublinBikesByTime(@PathVariable String stime, @PathVariable String etime) {
		String response = null;
		try {
			response = gson().toJson(this.bikesTSService.findBikesByTime(Long.parseLong(stime), Long.parseLong(etime)),
					new TypeToken<List<DBikesStationTimestamp>>() {}.getType());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (LOGGER.isDebugEnabled()) {
		  LOGGER.debug("Content: " + response);
		}
		return response;
	}
	  
	@RequestMapping(value={"/delete/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public void deleteById(@PathVariable Integer id) {
		this.bikesTSService.delete(id);
	}

}
