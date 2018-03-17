package tcd.ie.dublinbikes.db.repo;

import org.springframework.data.repository.CrudRepository;

import tcd.ie.dublinbikes.db.entity.DBikesStation;
/**
 * 
 * @author arun
 *
 */

public abstract interface IDBikesStationRepo extends CrudRepository<DBikesStation, Integer>{

}
