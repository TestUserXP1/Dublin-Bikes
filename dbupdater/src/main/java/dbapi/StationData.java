package dbapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StationData {
	
	private Station station;
	
    public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public StationData() {
    }
	
    @Override
    public String toString() {
        return "StationData{" +
                "Station=" + station +
                '}';
    }
	
}

