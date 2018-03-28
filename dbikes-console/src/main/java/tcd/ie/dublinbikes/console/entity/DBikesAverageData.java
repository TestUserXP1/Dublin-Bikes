package tcd.ie.dublinbikes.console.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author arun
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect
public class DBikesAverageData {
	
	@JsonProperty("number")
	private Integer number;
	
	@JsonProperty("bike_stands")
	private String bike_stands;
	
	@JsonProperty("address")
	private String address;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("avg_available_bike_stands")
	private String avg_available_bike_stands;
	
	@JsonProperty("position")
	private Position position;
	
	@JsonProperty("avg_available_bikes")
	private String avg_available_bikes;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getBike_stands() {
		return bike_stands;
	}
	public void setBike_stands(String bike_stands) {
		this.bike_stands = bike_stands;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvg_available_bike_stands() {
		return avg_available_bike_stands;
	}
	public void setAvg_available_bike_stands(String avg_available_bike_stands) {
		this.avg_available_bike_stands = avg_available_bike_stands;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public String getAvg_available_bikes() {
		return avg_available_bikes;
	}
	public void setAvg_available_bikes(String avg_available_bikes) {
		this.avg_available_bikes = avg_available_bikes;
	}

}
