package tcd.ie.dublinbikes.db.repo;

import java.util.List;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tcd.ie.dublinbikes.db.entity.DBikesStationTimestamp;
/**
 * 
 * @author arun
 *
 */

public abstract interface IDBikesStationTimestampRepo extends CrudRepository<DBikesStationTimestamp, Integer>{
	
	@Query("select * from dublin_bikes_json2 where number=:id")
	public List<DBikesStationTimestamp> findBikesById(@Param("id") Integer id);
	
	@Query("select * from dublin_bikes_json2 where number=:id and last_update >= :sTime and last_update < :eTime")
	public List<DBikesStationTimestamp> findBikesByIdTimestamp(@Param("id") Integer id, @Param("sTime") Long sTime, 
			@Param("eTime") Long eTime);
	
	@Query("select * from dublin_bikes_json2 where last_update >= :sTime and last_update < :eTime allow filtering")
	public List<DBikesStationTimestamp> findBikesByTimestamp(@Param("sTime") Long sTime, 
			@Param("eTime") Long eTime);

}
