package tcd.ie.dublinbikes.console.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author arun
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DBikesPredictData {

	private String number;

	private String day;

	private String hour;

	private String score;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

}
