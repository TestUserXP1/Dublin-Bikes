package tcd.ie.dublinbikes.console.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import tcd.ie.dublinbikes.console.util.AppPropertyLoader;
import tcd.ie.dublinbikes.console.util.RestClient;

/**
 * 
 * @author arun
 *
 */
@Component
public class TimeSeriesHelper {
	
	AppPropertyLoader propLoad = null;
	
	RestClient restClient = null;

	public TimeSeriesHelper() {
		propLoad = new AppPropertyLoader();
		restClient = new RestClient();
	}
	
	public String getDataFromDb(String id, ArrayList<Long> time) {

		String host = propLoad.getProperty("dbikes.db.host");
		String uri = propLoad.getProperty("dbikes.db.uri");
		// URI parameters
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("id", id);
		uriParams.put("stime", time.get(1).toString());
		uriParams.put("etime", time.get(0).toString());

		return restClient.getRequest(host + uri, uriParams, String.class);
	}
}
