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
	private List<GraphAxis> graphAxis;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<GraphAxis> getGraphAxis() {
		return graphAxis;
	}
	public void setGraphAxis(List<GraphAxis> graphAxis) {
		this.graphAxis = graphAxis;
	}
}
