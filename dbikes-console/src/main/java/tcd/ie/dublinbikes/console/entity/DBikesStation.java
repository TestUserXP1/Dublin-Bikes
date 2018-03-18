package tcd.ie.dublinbikes.console.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/**
 * 
 * @author arun
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DBikesStation {
	
	private Integer number;
	private long last_update;
	private String address;
	private BigDecimal available_bike_stands;
	private String available_bikes;
	private boolean banking;
	private Integer bike_stands;
	private boolean bonus;
	private String name;
	private Position position;
	private String status;
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public long getLast_update() {
		return last_update;
	}
	public void setLast_update(long last_update) {
		this.last_update = last_update;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigDecimal getAvailable_bike_stands() {
		return available_bike_stands;
	}
	public void setAvailable_bike_stands(BigDecimal available_bike_stands) {
		this.available_bike_stands = available_bike_stands;
	}
	public String getAvailable_bikes() {
		return available_bikes;
	}
	public void setAvailable_bikes(String available_bikes) {
		this.available_bikes = available_bikes;
	}
	public boolean isBanking() {
		return banking;
	}
	public void setBanking(boolean banking) {
		this.banking = banking;
	}
	public Integer getBike_stands() {
		return bike_stands;
	}
	public void setBike_stands(Integer bike_stands) {
		this.bike_stands = bike_stands;
	}
	public boolean isBonus() {
		return bonus;
	}
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
