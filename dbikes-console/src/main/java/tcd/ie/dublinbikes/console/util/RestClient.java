package tcd.ie.dublinbikes.console.util;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author arun
 *
 */
public class RestClient {

	RestTemplate restTemplate;

	public RestClient() {
		restTemplate = new RestTemplate();
	}

	public String getRequest(String URI, Map<String, String> uriParams, Class<?> responseType) {
		String response = null;
		try {
			System.out.println("Begin GET request!:" + URI + ":" + uriParams);
			ResponseEntity<?> getResponse = restTemplate.getForEntity(URI, responseType, uriParams);
			//response = restTemplate.getForObject(URI, String.class, uriParams);
			if (getResponse.getBody() != null) {
				response = getResponse.getBody().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public String postRequest(String URI, Object reqObj, Class<?> responseType) {
		String response = null;
		try {
			System.out.println("Begin GET request!:" + URI);
			HttpEntity<Object> request = new HttpEntity<Object>(reqObj);
			ResponseEntity<?> getResponse = restTemplate.exchange(URI, HttpMethod.POST, request, responseType);
			if (getResponse.getBody() != null) {
				response = getResponse.getBody().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
}
