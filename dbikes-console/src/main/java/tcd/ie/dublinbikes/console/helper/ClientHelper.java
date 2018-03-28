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
public class ClientHelper {
	
	AppPropertyLoader propLoad = null;
	
	RestClient restClient = null;

	public ClientHelper() {
		propLoad = new AppPropertyLoader();
		restClient = new RestClient();
	}
	
	public String getDataFromDb(String id, ArrayList<String> time) {

		String host = propLoad.getProperty("dbikes.db.host");
		String uri = propLoad.getProperty("dbikes.db.uri");
		// URI parameters
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("id", id);
		uriParams.put("stime", time.get(1).toString());
		uriParams.put("etime", time.get(0).toString());

		return restClient.getRequest(host + uri, uriParams, String.class);
	}
	
	public String getAverageDataFromDB(String id, ArrayList<String> time) {
		
		String host = propLoad.getProperty("dbikes.db.host");
		String uri = propLoad.getProperty("dbikes.db.uri.avg");
		// URI parameters
		Map<String, String> uriParams = new HashMap<String, String>();
		uriParams.put("id", id);
		uriParams.put("stime", time.get(1).toString());
		uriParams.put("etime", time.get(0).toString());
		uriParams.put("avg", "true");

		return restClient.getRequest(host + uri, uriParams, String.class);
		
	}
	
	public String getPredictDataFromS3() {	
		String host = propLoad.getProperty("dbikes.predict.host");
		String uri = propLoad.getProperty("dbikes.predict.uri");
		Map<String, String> uriParams = new HashMap<String, String>();	
		return restClient.getRequest(host + uri, uriParams ,String.class);
		
	}
}
