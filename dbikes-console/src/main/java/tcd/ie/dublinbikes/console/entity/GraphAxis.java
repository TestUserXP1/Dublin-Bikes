package tcd.ie.dublinbikes.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author arun
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphAxis {
	
	private String xAxis;
	private String yAxis;
	
	public String getxAxis() {
		return xAxis;
	}
	public void setxAxis(String xAxis) {
		this.xAxis = xAxis;
	}
	public String getyAxis() {
		return yAxis;
	}
	public void setyAxis(String yAxis) {
		this.yAxis = yAxis;
	}
}
