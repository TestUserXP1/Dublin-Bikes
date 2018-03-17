package tcd.ie.dublinbikes.db.services;

import java.text.ParseException;
import java.util.List;

import tcd.ie.dublinbikes.db.entity.DBikesStationTimestamp;

/**
 * 
 * @author arun
 *
 */
public abstract interface IDBikesStationTimestampService {
	
	
	public abstract List<DBikesStationTimestamp> listAll();
	  
	public abstract List<DBikesStationTimestamp> findBikesById(Integer id);
	
	public abstract List<DBikesStationTimestamp> findBikesByIdTime(Integer id, Long stime, Long etime) throws ParseException;
	
	public abstract List<DBikesStationTimestamp> findBikesByTime(Long stime, Long etime) throws ParseException;
	  
	public abstract DBikesStationTimestamp saveOrUpdate(DBikesStationTimestamp dbikes);
	  
	public abstract void delete(Integer id);

}
