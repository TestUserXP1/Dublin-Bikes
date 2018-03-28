package tcd.ie.dublinbikes.console.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * 
 * @author arun
 *
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class DBikesStationWrapper {
	
	@JsonProperty("dbikesdata")
	private List<DBikesStationData> dbikesdata;

	public List<DBikesStationData> getDbikes() {
		return dbikesdata;
	}

	public void setDbikes(List<DBikesStationData> dbikes) {
		this.dbikesdata = dbikes;
	}
	
	public String toString() {
		return dbikesdata.toString();
	}


}
