package tcd.ie.dublinbikes.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 
 * @author arun
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Position {

	private Double lat;
	private Double lng;
	
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}

}
