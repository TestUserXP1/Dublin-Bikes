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
public class DBikesAverageWrapper {
	
	@JsonProperty("dbikesdata")
	private List<DBikesAverageData> dbikesdata;

	public List<DBikesAverageData> getDbikesData() {
		return dbikesdata;
	}

	public void setDbikesData(List<DBikesAverageData> dbikesData) {
		this.dbikesdata = dbikesData;
	}


}
