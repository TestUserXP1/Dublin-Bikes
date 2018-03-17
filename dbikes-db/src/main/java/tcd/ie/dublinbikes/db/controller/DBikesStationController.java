package tcd.ie.dublinbikes.db.controller;

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

import tcd.ie.dublinbikes.db.entity.DBikesStation;
import tcd.ie.dublinbikes.db.services.IDBikesStationService;

/**
 * 
 * @author arun
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping({"/dbikes/raw"})
public class DBikesStationController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DBikesStationController.class);
	
	@Autowired
	private IDBikesStationService bikesService;
	  
	@Bean
	public Gson gson() {
		return new Gson();
	}
	  
	@RequestMapping(value={"/list"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String listDublinBikes() {
		String response = gson().toJson(this.bikesService
		      .listAll(), new TypeToken<List<DBikesStation>>() {}.getType());
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Content: " + response);
		}
		return response;
	}
  
	@RequestMapping(value={"/view/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getDublinBikesById(@PathVariable Integer id) {
		String response = gson().toJson(this.bikesService.getById(id));
		if (LOGGER.isDebugEnabled()) {
		  LOGGER.debug("Content: " + response);
		}
		return response;
	}
	  
	@RequestMapping(value={"/delete/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String deleteById(@PathVariable Integer id) {
		this.bikesService.delete(id);
		return "redirect:/dublinbikes/list";
	}
  
}

