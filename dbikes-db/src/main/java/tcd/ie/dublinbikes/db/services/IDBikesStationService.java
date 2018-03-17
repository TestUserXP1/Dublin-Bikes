package tcd.ie.dublinbikes.db.services;

import java.util.List;

import tcd.ie.dublinbikes.db.entity.DBikesStation;

/**
 * 
 * @author arun
 *
 */
public abstract interface IDBikesStationService {
	
	public abstract List<DBikesStation> listAll();
	  
	public abstract DBikesStation getById(Integer id);
	  
	public abstract DBikesStation saveOrUpdate(DBikesStation dbikes);
	  
	public abstract void delete(Integer id);

}
