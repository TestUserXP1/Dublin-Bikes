package tcd.ie.dublinbikes.db.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tcd.ie.dublinbikes.db.entity.DBikesStation;
import tcd.ie.dublinbikes.db.repo.IDBikesStationRepo;
import tcd.ie.dublinbikes.db.services.IDBikesStationService;

/**
 * 
 * @author arun
 *
 */
@Service
public class DBikesStationServiceImpl implements IDBikesStationService {
	
	
	@Autowired
	private IDBikesStationRepo dbikesRepository;
  
    @Override
    public List<DBikesStation> listAll() {
        List<DBikesStation> dbikes = new ArrayList<>();
        dbikesRepository.findAll().forEach(dbikes::add); 
        return dbikes;
    }
    
	@Override
	public DBikesStation getById(Integer id) {
		return dbikesRepository.findById(id).orElse(null);
	}

	@Override
	public DBikesStation saveOrUpdate(DBikesStation dbikes) {
		dbikesRepository.save(dbikes);
        return dbikes;
	}

	@Override
	public void delete(Integer id) {
		dbikesRepository.deleteById(id);	
	}

}
