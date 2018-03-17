package tcd.ie.dublinbikes.db.services.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tcd.ie.dublinbikes.db.entity.DBikesStationTimestamp;
import tcd.ie.dublinbikes.db.repo.IDBikesStationTimestampRepo;
import tcd.ie.dublinbikes.db.services.IDBikesStationTimestampService;
/**
 * 
 * @author arun
 *
 */
@Service
public class DBikesStationTimestampServiceImpl implements IDBikesStationTimestampService {

	@Autowired
	private IDBikesStationTimestampRepo dbikesTSRepository;
	
	@Override
	public List<DBikesStationTimestamp> listAll() {
		List<DBikesStationTimestamp> dbikes = new ArrayList<>();
		dbikesTSRepository.findAll().forEach(dbikes::add);
        return dbikes;
	}

	@Override
	public List<DBikesStationTimestamp> findBikesById(Integer id) {
		List<DBikesStationTimestamp> dbikes = new ArrayList<>();
		dbikes = dbikesTSRepository.findBikesById(id);
		return dbikes;
	}
	
	@Override
	public List<DBikesStationTimestamp> findBikesByIdTime(Integer id, Long sTime, Long eTime) throws ParseException {	    
		List<DBikesStationTimestamp> dbikes = new ArrayList<>();
		dbikes = dbikesTSRepository.findBikesByIdTimestamp(id, sTime, eTime);
		return dbikes;
	}
	
	@Override
	public List<DBikesStationTimestamp> findBikesByTime(Long sTime, Long eTime) throws ParseException {	    
		List<DBikesStationTimestamp> dbikes = new ArrayList<>();
		dbikes = dbikesTSRepository.findBikesByTimestamp(sTime, eTime);
		return dbikes;
	}

	@Override
	public DBikesStationTimestamp saveOrUpdate(DBikesStationTimestamp dbikes) {
		dbikesTSRepository.save(dbikes);
        return dbikes;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
