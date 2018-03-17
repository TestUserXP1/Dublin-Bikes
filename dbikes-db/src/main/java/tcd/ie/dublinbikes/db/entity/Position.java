package tcd.ie.dublinbikes.db.entity;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import com.datastax.driver.core.DataType.Name;
/**
 * 
 * @author arun
 *
 */

@UserDefinedType("position")
public class Position implements Serializable {

	@CassandraType(type=Name.DOUBLE)
	private Double lat;
	
	@CassandraType(type=Name.DOUBLE)
	private Double lng;

}
