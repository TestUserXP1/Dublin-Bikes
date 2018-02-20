package dbapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Station{
	
	private int number;
	private String name;
	private String address;
	private Position position;
	private boolean banking;
	private boolean bonus;
	private String status;
	private int bike_stands;
	private int available_bike_stands;
	private int available_bikes;
	private long last_update;
	
    public Station() {
    }
    
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public boolean isBanking() {
		return banking;
	}
	public void setBanking(boolean banking) {
		this.banking = banking;
	}
	public boolean isBonus() {
		return bonus;
	}
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getBike_stands() {
		return bike_stands;
	}
	public void setBike_stands(int bike_stands) {
		this.bike_stands = bike_stands;
	}
	public int getAvailable_bike_stands() {
		return available_bike_stands;
	}
	public void setAvailable_bike_stands(int available_bike_stands) {
		this.available_bike_stands = available_bike_stands;
	}
	public int getAvailable_bikes() {
		return available_bikes;
	}
	public void setAvailable_bikes(int available_bikes) {
		this.available_bikes = available_bikes;
	}
	public long getLast_update() {
		return last_update;
	}
	public void setLast_update(long last_update) {
		this.last_update = last_update;
	}
	
	
    @Override
    public String toString() {
        return "Station{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", available_bike_stands='" + available_bike_stands + '\'' +
                ", available_bikes='" + available_bikes + '\'' +
                ", position='" + position + '\'' +
                ", address=" + address +
                '}';
    }
	
}

