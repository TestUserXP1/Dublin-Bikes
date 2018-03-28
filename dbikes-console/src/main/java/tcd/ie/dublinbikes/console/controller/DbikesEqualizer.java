package tcd.ie.dublinbikes.console.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import tcd.ie.dublinbikes.console.helper.ClientHelper;

/**
 * 
 * @author arun
 *
 */
@Controller
@EnableAutoConfiguration
@RequestMapping({"/dbeq"})
public class DbikesEqualizer {
	
	@Autowired
	ClientHelper cHelper;
	
	
	/*@RequestMapping(value={"/pred/nhr/{id}"}, method={RequestMethod.GET}, produces={"application/json"})
	@ResponseBody
	public String getHourInfoById(@PathVariable Integer id) {
		
		ComputeTime ct = new ComputeTime();
		ArrayList<String> time = ct.hourTimeDiff();
		String dataResp = cHelper.getDataFromDb(id.toString(), time);
		
		System.out.println(dataResp);
		TimeGraphResponse tgRes = tsService.getHourlyPlotData(String.valueOf(id), dataResp);
		
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = mapper.writeValueAsString(tgRes);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return response;
	}*/
	
}
