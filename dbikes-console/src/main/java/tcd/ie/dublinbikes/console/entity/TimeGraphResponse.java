package tcd.ie.dublinbikes.console.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 
 * @author arun
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeGraphResponse {
	
	private String id;
	
	private List<GraphCoord> mean;

	private List<GraphCoord> predict;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public List<GraphCoord> getPredict() {
		return predict;
	}
	public void setPredict(List<GraphCoord> predict) {
		this.predict = predict;
	}
	public List<GraphCoord> getMean() {
		return mean;
	}
	public void setMean(List<GraphCoord> mean) {
		this.mean = mean;
	}

	
}
